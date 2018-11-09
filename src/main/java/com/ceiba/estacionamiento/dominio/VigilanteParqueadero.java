package com.ceiba.estacionamiento.dominio;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.exception.EstacionamientoException;
import com.ceiba.estacionamiento.modelo.Vehiculo;
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
	
	private static final String ESTA_AUTORIZADO_A_INGRESAR = "esta autorizado a ingresar";
	private static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "no puede ingresar porque no esta en un dia habil";
	private static final String NO_PUEDE_INGRESAR_PARQUEADERO_LLENO = "no puede ingresar porque el parqueadero esta lleno";
	
	//cambiar a String
	public void ingresarVehiculo(Vehiculo vehiculo,Date fechaIngreso) throws EstacionamientoException {
		LOGGER.info("entra al metodo ingresar vehiculo");
		String mensajeValidacion = validarIngresoVehiculo(vehiculo, fechaIngreso);
		LOGGER.info(mensajeValidacion);
		if( ESTA_AUTORIZADO_A_INGRESAR.equals( mensajeValidacion ) ) {
			LOGGER.info("entra al if vehiculo autorizado para el ingreso");
			facturaDao.guardarFactura(new FacturaEntity(vehiculo, fechaIngreso,true));
		}else {
			throw new EstacionamientoException(mensajeValidacion);
		}
	}
	 
	public List<Vehiculo> obtenerVehiculosParqueados() {
		return facturaDao.obtenerVehiculosParqueados();
	}
	
	
	
	private String validarIngresoVehiculo(Vehiculo vehiculo,Date fechaIngreso) {
		LOGGER.info("entra al metodo  validarIngresoVehiculo");	
		
		//validar vehiculo y fecha
		vehiculoValidacion.tipoVehiculoEsValido(vehiculo.getTipo());
		
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
			LOGGER.debug(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
			return false;
		}
		return true;
	}
	
	private boolean validarIngresoPorDisponibilidad(int tipoVehiculo) {
		return vehiculoValidacion.espacioParaParqueoDisponible(tipoVehiculo);
	}
	
	public FacturaEntity registrarSalidaVehiculo (String placa) throws EstacionamientoException {
		LOGGER.info("entro a registrar salida vigilante");
		FacturaEntity factura = facturaDao.obtenerFacturaVehiculoParqueadoPorPlaca(placa);
		if(factura == null ) {
			throw new EstacionamientoException("El vehiculo no se encuentra parqueado");
		}
		factura.setFechaSalida(new Date());
		LOGGER.info(factura.getFechaIngreso().toString());
		LOGGER.info(factura.getFechaSalida().toString());
		factura.setValor(calcularCostoFactura(factura));
		factura.setParqueado(false);
		facturaDao.actualizarFactura(factura);
		return factura;
	}
	 
	private double calcularCostoFactura(FacturaEntity factura){
		CalculadoraCostoParqueo calculadoraCosto;
		double costoParqueo;
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
	    	  calculadoraCosto = null;
	           break;
		}
		costoParqueo = (calculadoraCosto != null ? calculadoraCosto.calcularCostoFactura(factura) : 0);
		return costoParqueo;
	}


}
