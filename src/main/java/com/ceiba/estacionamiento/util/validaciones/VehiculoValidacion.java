package com.ceiba.estacionamiento.util.validaciones;

import java.util.Calendar;
import java.util.Date;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.exception.EstacionamientoException;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.util.Constantes;


@Component
public class VehiculoValidacion {
	
	private static final Logger LOGGER = LogManager.getLogger(VehiculoValidacion.class);
	
	private static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "No puede ingresar porque no esta en un dia habil";
	private static final String NO_PUEDE_INGRESAR_PARQUEADERO_LLENO = "No puede ingresar porque el parqueadero esta lleno";
	private static final String DATOS_DEL_VEHICULO_NO_SON_VALIDOS_PARA_EL_INGRESO = "Datos del vehiculo no son validos para el ingreso";
	
	private @Autowired
	FacturaDao factDao;
	
	public void validarVehiculo (Vehiculo vehiculo) throws EstacionamientoException {
		validarPlaca(vehiculo.getPlaca());
		validarTipoDeVehiculo(vehiculo.getTipo());
		validarCilindraje(vehiculo.getCilindraje());
	}
	
	private void validarPlaca(String placaVehiculo) throws EstacionamientoException {
		if(placaVehiculo == null || placaVehiculo.trim().isEmpty()) {
			LOGGER.info("la placa no es valida");
			throw new EstacionamientoException(DATOS_DEL_VEHICULO_NO_SON_VALIDOS_PARA_EL_INGRESO);
		}
	}
	
	private void validarTipoDeVehiculo(int tipo) throws EstacionamientoException {
		if(!tipoVehiculoEsValido(tipo)) {
			LOGGER.info("el tipo de vehiculo no es valido");
			throw new EstacionamientoException(DATOS_DEL_VEHICULO_NO_SON_VALIDOS_PARA_EL_INGRESO);
		}
	}
	
	private boolean tipoVehiculoEsValido(int tipoVehiculo){
		if(tipoVehiculo==Constantes.CODIGO_VEHICULO_MOTO || tipoVehiculo==Constantes.CODIGO_VEHICULO_CARRO) {
			LOGGER.debug("El tipo de vehiculo ingrasado es valido");
			return true;
		}
		return false;
	}
	
	private void validarCilindraje(int cilindaje) throws EstacionamientoException {
		if(cilindaje<1) {
			LOGGER.info("el cilindraje del vehiculo no es valido");
			throw new EstacionamientoException(DATOS_DEL_VEHICULO_NO_SON_VALIDOS_PARA_EL_INGRESO);
		}
	}
	
	public void vetificarSiFechaDeIngresoEsHabilParaLaPlacaDelVehiculo(String placaVehiculo, Date fechaIngreso) throws EstacionamientoException {
		if(placaIniciaConletraA(placaVehiculo) && !diaEsDomingoOLunes(fechaIngreso)) {
			LOGGER.info("Placa inicia con letra A y día de ingreso es distinto a lunes o domingo");
			throw new EstacionamientoException(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
		}
	}
	
	private boolean placaIniciaConletraA(String placaVehiculo) {
		placaVehiculo = placaVehiculo.toUpperCase();
		return placaVehiculo.startsWith("A");
	}
	
	private boolean diaEsDomingoOLunes(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int diaSemana =  calendar.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana==Calendar.SUNDAY || diaSemana==Calendar.MONDAY){
		   LOGGER.debug("fecha ingresada es un domingo o un lunes");
		   return true;
		}
		return false;
	}
	
	public void validarEspaciDeParqueoDisponiblePorTipoDeVehiculo(int tipoVehiculo) throws EstacionamientoException {
		if (factDao.contarVehiculosParqueadosPorTipo(tipoVehiculo)>= limitePorTipoVehiculo(tipoVehiculo)) {
			LOGGER.debug("No se cuenta con espacio para parquear vehiculos de tipo: "+tipoVehiculo );
			throw new EstacionamientoException(NO_PUEDE_INGRESAR_PARQUEADERO_LLENO);
		}
	}
	
	private  int limitePorTipoVehiculo(int tipoVehiculo) {
		int limite = 0;
		if(tipoVehiculo == Constantes.CODIGO_VEHICULO_MOTO) {
			limite = Constantes.PARQUEADERO_CEIBA_LIMITE_MOTOS;
		}
		if(tipoVehiculo == Constantes.CODIGO_VEHICULO_CARRO) {
			limite = Constantes.PARQUEADERO_CEIBA_LIMITE_CARROS;
		}
		return limite;
	}

}
