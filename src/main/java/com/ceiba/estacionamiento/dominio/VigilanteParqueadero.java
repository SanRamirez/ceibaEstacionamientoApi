package com.ceiba.estacionamiento.dominio;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.dominio.builder.FacturaBuilder;
import com.ceiba.estacionamiento.exception.EstacionamientoException;
import com.ceiba.estacionamiento.modelo.Factura;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.modelo.VehiculoIngresado;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;
import com.ceiba.estacionamiento.util.validaciones.VehiculoValidacion;

@Component
public class VigilanteParqueadero {
	
	@Autowired
	private FacturaDao facturaDao;
	@Autowired
	private VehiculoValidacion vehiculoValidacion;
	private static final Logger LOGGER = LogManager.getLogger(VigilanteParqueadero.class);
	
	private static final String ESTA_AUTORIZADO_A_INGRESAR = "Esta autorizado a ingresar";
	private static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "No puede ingresar porque no esta en un dia habil";
	private static final String NO_PUEDE_INGRESAR_PARQUEADERO_LLENO = "No puede ingresar porque el parqueadero esta lleno";
	private static final String DATOS_DEL_VEHICULO_NO_SON_VALIDOS_PARA_EL_INGRESO = "Datos del vehiculo no son validos para el ingreso";
	private static final String FECHA_INGRESO_ES_NULA= "Fecha de ingreso es nula";
	private static final String EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO = "El vehiculo no se encuentra parqueado";
	private static final String EL_VEHICULO_YA_SE_ENCUENTRA_PARQUEADO = "El vehiculo ya se encuentra parqueado";
	
	
	public void ingresarVehiculo(Vehiculo vehiculo) throws EstacionamientoException {
		boolean parqueado = true;
		Date fechaIngreso = new Date(); 
		String mensajeValidacion = validarVehiculoYFechaDeIngreso(vehiculo, fechaIngreso);
		LOGGER.info(mensajeValidacion);
		if( ESTA_AUTORIZADO_A_INGRESAR.equals( mensajeValidacion ) ) {
			facturaDao.guardarFactura(new FacturaEntity(vehiculo, fechaIngreso,parqueado));
		}else {
			throw new EstacionamientoException(mensajeValidacion);
		}
	}
	 
	public List<VehiculoIngresado> obtenerVehiculosParqueados() {
		return facturaDao.obtenerVehiculosParqueados();
	}
	
	
	private String validarVehiculoYFechaDeIngreso(Vehiculo vehiculo,Date fechaIngreso) {
		LOGGER.info("entra al metodo  validarIngresoVehiculo");	
		
		if (!vehiculoValidacion.datosDelVehiculoValidosParaIngreso(vehiculo)) {
			return DATOS_DEL_VEHICULO_NO_SON_VALIDOS_PARA_EL_INGRESO;
		}
		if (fechaIngreso == null) {
			return FECHA_INGRESO_ES_NULA; 
		}
		if(vehiculoEstaParqueado(vehiculo.getPlaca())) {
			return EL_VEHICULO_YA_SE_ENCUENTRA_PARQUEADO;
		}
		if (!validarIngresoPorPlacaParaFechaIngreso(vehiculo.getPlaca(), fechaIngreso)) {
			return NO_PUEDE_INGRESAR_DIA_NO_HABIL;
		}
		if (!validarIngresoPorDisponibilidad(vehiculo.getTipo())) {
			return NO_PUEDE_INGRESAR_PARQUEADERO_LLENO;
		}
		return ESTA_AUTORIZADO_A_INGRESAR;
	}
	
	
	private boolean validarIngresoPorPlacaParaFechaIngreso(String placaVehiculo, Date fechaIngreso) {
		if(vehiculoValidacion.placaIniciaConletraA(placaVehiculo) && !vehiculoValidacion.diaEsDomingoOLunes(fechaIngreso)) {
			LOGGER.info(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
			return false;
		}
		return true;
	}
	
	private boolean validarIngresoPorDisponibilidad(int tipoVehiculo) {
		return vehiculoValidacion.espacioParaParqueoDisponible(tipoVehiculo);
	}
	
	private boolean vehiculoEstaParqueado(String placaVehiculo) {
		boolean estaParqueado = true;
		FacturaEntity factura = facturaDao.obtenerFacturaVehiculoParqueadoPorPlaca(placaVehiculo);
		if(factura == null ) {
			estaParqueado = false;
		}
		return estaParqueado;
	}
	
	public Factura registrarSalidaVehiculo (String placa) throws EstacionamientoException {
		LOGGER.info("entro a registrar salida vigilante");
		FacturaEntity facturaEntity = facturaDao.obtenerFacturaVehiculoParqueadoPorPlaca(placa);
		Factura factura;
		if(facturaEntity == null ) {
			throw new EstacionamientoException(EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO);
		}
		facturaEntity.setFechaSalida(new Date());
		facturaEntity = calcularCostoFactura(facturaEntity);
		facturaEntity.setParqueado(false);
		facturaDao.actualizarFactura(facturaEntity);
		factura = FacturaBuilder.convertirADominio(facturaEntity);
		LOGGER.info("factura a retornar despues de registar la salida");
		LOGGER.info(factura.toString());
		return factura;
	}
	 
	private FacturaEntity calcularCostoFactura(FacturaEntity factura) throws EstacionamientoException{
		CalculadoraCostoParqueo calculadoraCosto;
		LOGGER.info("tipo vehiculo "+factura.getTipoVehiculo());
		switch ( factura.getTipoVehiculo() ) {
	      case Constantes.CODIGO_VEHICULO_MOTO:
	    	   LOGGER.info("entra al strategy moto");
	           calculadoraCosto = new CalculadoraCostoParqueoMoto();
	           break;
	      case Constantes.CODIGO_VEHICULO_CARRO:
	    	  	LOGGER.info("entra al strategy carro");
	    	  	calculadoraCosto = new CalculadoraCostoParqueoCarro(); 
	    	  	break;
	      default:
	    	  throw new EstacionamientoException("El tipo de vehiculo que contiene la factura no se encuentra registrado");
		}
		factura = calculadoraCosto.calcularCostoFactura(factura);
		return factura;
	}
}
