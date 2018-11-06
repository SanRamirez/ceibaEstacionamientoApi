package com.ceiba.estacionamiento.dominio;

import java.util.Date;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;
import com.ceiba.estacionamiento.util.validaciones.VehiculoValidacion;

@Component
public class VigilanteParqueadero {
	
	@Autowired
	FacturaDao facturaDao;
	@Autowired
	VehiculoValidacion vehiculoValidacion;
	private static final Logger LOGGER = LogManager.getLogger(VigilanteParqueadero.class);
	
	public static final String NO_ESTA_AUTORIZADO_A_INGRESAR = "no esta autorizado a ingresar";
	public static final String ESTA_AUTORIZADO_A_INGRESAR = "esta autorizado a ingresar";
	public static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "no puede ingresar porque no esta en un dia habil";
	public static final String NO_PUEDE_INGRESAR_PARQUEADERO_LLENO = "no puede ingresar porque el parqueadero esta lleno";
	
	//cambiar a String
	public void ingresarVehiculo(Vehiculo vehiculo,Date fechaIngreso) {
		LOGGER.info("entra al metodo ingresar vehiculo");
		String mensajeValidacion = validarIngresoVehiculo(vehiculo, fechaIngreso);
		LOGGER.info(mensajeValidacion);
		if( ESTA_AUTORIZADO_A_INGRESAR.equals( mensajeValidacion ) ) {
			LOGGER.info("entra al if vehiculo autorizado para el ingreso");
			facturaDao.guardarFactura(new FacturaEntity(vehiculo, fechaIngreso,true));
		}
	}
	
	public String validarIngresoVehiculo(Vehiculo vehiculo,Date fechaIngreso) {
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
	
	
	public boolean validarIngresoPorPlacaParaFechaIngreso(String placaVehiculo, Date fechaIngreso) {
		if(vehiculoValidacion.placaIniciaConletraA(placaVehiculo) && !vehiculoValidacion.diaEsDomingoOLunes(fechaIngreso)) {
			LOGGER.debug(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
			return false;
		}
		return true;
	}
	
	public boolean validarIngresoPorDisponibilidad(int tipoVehiculo) {
		if( !vehiculoValidacion.espacioParaParqueoDisponible(tipoVehiculo)) {
			LOGGER.debug(NO_PUEDE_INGRESAR_PARQUEADERO_LLENO);
			return false;
		}
		return true;
	}
	
	public FacturaEntity registrarSalidaVehiculo (String placa) {
		FacturaEntity factura = facturaDao.obtenerFacturaVeiculoParqueadoPorPlaca(placa);
		if(factura == null ) {
			return factura;
		}
		factura.setFechaSalida(new Date());
		factura = agregarCostoFactura(factura);
		factura.setParqueado(false);
		facturaDao.actualizarFactura(factura);
		return factura;
	}
	
	public FacturaEntity agregarCostoFactura(FacturaEntity factura){
		CalculadoraCostoParqueo calculadoraCosto;
		double costoParqueo;

		switch ( factura.getTipoVehiculo() ) {
	      case Constantes.CODIGO_VEHICULO_MOTO:
	           calculadoraCosto = new CalculadoraCostoParqueoMoto();
	           break;
	      case Constantes.CODIGO_VEHICULO_CARRO:
	    	  	calculadoraCosto = new CalculadoraCostoParqueoCarro(); 
	    	  	break;
	      default:
	    	  calculadoraCosto = null;
	           break;
		}
	
		costoParqueo = (calculadoraCosto != null ? calculadoraCosto.calcularCostoFactura(factura) : 0);
		factura.setValor(costoParqueo);
		
		return factura;
	}
}
