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
import com.ceiba.estacionamiento.util.validaciones.VehiculoValidacion;

@Component
public class VigilanteParqueadero {
	
	@Autowired
	private FacturaDao facturaDao;
	@Autowired
	private VehiculoValidacion vehiculoValidacion;
	@Autowired
	private CalculadoraCostoParqueoFactory calculadoraCostoParqueoFactory;
	

	private static final Logger LOGGER = LogManager.getLogger(VigilanteParqueadero.class);
	
	private static final String FECHA_INGRESO_ES_NULA= "Fecha de ingreso es nula";
	private static final String EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO = "El vehiculo no se encuentra parqueado";
	private static final String EL_VEHICULO_YA_SE_ENCUENTRA_PARQUEADO = "El vehiculo ya se encuentra parqueado";
	
	
	public void ingresarVehiculo(Vehiculo vehiculo,Date fechaIngreso) throws EstacionamientoException {
		boolean parqueado = true;
		
		validarVehiculoYFechaDeIngreso(vehiculo, fechaIngreso);
		facturaDao.guardarFactura(new FacturaEntity(vehiculo, fechaIngreso,parqueado));
	}
	 
	public List<VehiculoIngresado> obtenerVehiculosParqueados() {
		return facturaDao.obtenerVehiculosParqueados();
	}
	
	
	private void validarVehiculoYFechaDeIngreso(Vehiculo vehiculo,Date fechaIngreso) throws EstacionamientoException {
		LOGGER.info("entra al metodo  validarVehiculoYFechaDeIngreso");	
		
		vehiculoValidacion.validarVehiculo(vehiculo);
		if (fechaIngreso == null) {	
			throw new EstacionamientoException(FECHA_INGRESO_ES_NULA);
		}
		if(vehiculoEstaParqueado(vehiculo.getPlaca())) {
			throw new EstacionamientoException(EL_VEHICULO_YA_SE_ENCUENTRA_PARQUEADO);
		}
		vehiculoValidacion.vetificarSiFechaDeIngresoEsHabilParaLaPlacaDelVehiculo(vehiculo.getPlaca(), fechaIngreso);
		vehiculoValidacion.validarEspaciDeParqueoDisponiblePorTipoDeVehiculo(vehiculo.getTipo());
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
		CalculadoraCostoParqueo calculadoraCosto = calculadoraCostoParqueoFactory.getCalculadoraCostoParqueo(factura.getTipoVehiculo());
		if (calculadoraCosto == null) {
			throw new EstacionamientoException("El tipo de vehiculo que contiene la factura no se encuentra registrado");
		}
		factura = calculadoraCosto.calcularCostoFactura(factura);
		return factura;
	}
}
