package com.ceiba.estacionamiento.dominio.integracion;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento.dominio.VigilanteParqueadero;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

import org.junit.Assert;


@SpringBootTest
@RunWith(SpringRunner.class)
public class VigilanteParqueaderoTest {
	
	@Autowired
	VigilanteParqueadero vigilanteParqueadero;
	
	@Autowired
	FacturaDao facturaDao;

	@Test
	public void testIngresarVehiculo() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		Date fechaIngreso = new Date();
		
		// act
		vigilanteParqueadero.ingresarVehiculo(vehiculo, fechaIngreso);

		// assert
		Assert.assertNotNull(facturaDao.obtenerVeiculoParqueadoPorPlaca(vehiculo.getPlaca()));
	}
	
	@Test
	public void testIngresarVehiculoPlacaIniciaConAEnDiaNoHabil() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("AGD-512").build();
		Date fechaIngreso = new Date(2018,10,29);
		
		// act
		vigilanteParqueadero.ingresarVehiculo(vehiculo, fechaIngreso);

		// assert
		Assert.assertNull(facturaDao.obtenerVeiculoParqueadoPorPlaca(vehiculo.getPlaca()));
	}
	
	@Test
	public void testRegistrarSalidaVehiculo() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("CBA-210").build();
		vehiculo.setTipo(2);
		Calendar calendar = new GregorianCalendar(2018,Calendar.NOVEMBER,1,8,0,0);
		Date fechaIngreso = calendar.getTime();
		
		// act
		vigilanteParqueadero.ingresarVehiculo(vehiculo, fechaIngreso);
		vigilanteParqueadero.registrarSalidaVehiculo(vehiculo.getPlaca());

		// assert
		Assert.assertNull(facturaDao.obtenerVeiculoParqueadoPorPlaca(vehiculo.getPlaca()));
	}
	
	@Test
	public void testObtenerVehiculosParqueados() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("CBA-210").build();
		vehiculo.setTipo(2);
		Calendar calendar = new GregorianCalendar(2018,Calendar.NOVEMBER,1,8,0,0);
		Date fechaIngreso = calendar.getTime();
		
		// act
		vigilanteParqueadero.ingresarVehiculo(vehiculo, fechaIngreso);
		vigilanteParqueadero.registrarSalidaVehiculo(vehiculo.getPlaca());

		// assert
		Assert.assertNull(facturaDao.obtenerVeiculoParqueadoPorPlaca(vehiculo.getPlaca()));
	}


}
