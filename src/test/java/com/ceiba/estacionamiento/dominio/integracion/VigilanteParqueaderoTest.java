package com.ceiba.estacionamiento.dominio.integracion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento.dominio.VigilanteParqueadero;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class VigilanteParqueaderoTest {
	
	@Autowired
	VigilanteParqueadero vigilanteParqueadero;
	
	@Autowired
	FacturaDao facturaDao;
	private static final Logger LOGGER = LogManager.getLogger(VigilanteParqueaderoTest.class);

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
		Date fechaIngreso = new GregorianCalendar(2018,Calendar.NOVEMBER,1,8,0,0).getTime();
		
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
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("CBA-211").build();
		vehiculo1.setTipo(2);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("DBA-310").build();
		Calendar calendar = new GregorianCalendar(2018,Calendar.NOVEMBER,1,8,0,0);
		Date fechaIngreso = calendar.getTime();
		List<Vehiculo> vehiculosObtenidos;
		List<Vehiculo> vehiculosEsperados = new ArrayList<>();
		vehiculosEsperados.add(vehiculo1);
		vehiculosEsperados.add(vehiculo2);
		//restauramos la tabla para que solo quede con la informacion que vamos a ingresar 
		facturaDao.eliminarTodo();
		//FacturaDao facturaDaoMock  = mock(FacturaDao.class);
		//when(facturaDaoMock.obtenerVehiculosParqueados()).thenReturn(vehiculosEsperados);
		
		
		// act
		vigilanteParqueadero.ingresarVehiculo(vehiculo1, fechaIngreso);
		vigilanteParqueadero.ingresarVehiculo(vehiculo2, fechaIngreso);
		vehiculosObtenidos = vigilanteParqueadero.obtenerVehiculosParqueados();


		// assert
		Assert.assertTrue(vehiculosObtenidos.size() == vehiculosEsperados.size());
	}


}
