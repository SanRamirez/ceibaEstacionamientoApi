package com.ceiba.estacionamiento.dominio.unitaria;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento.dominio.CalculadoraTiempoParqueo;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculadoraTiempoParqueoTest {
	
	@Autowired
	CalculadoraTiempoParqueo calculadoraTiempoParqueo;

	@Test
	public void  testObtenerHorasParqueoTotales() {
		// arrange
		Calendar calendar = new GregorianCalendar(2018,Calendar.NOVEMBER,1,8,0,0);
		Date fechaIngreso = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.HOUR, 10);
		Date fechaSalida = calendar.getTime();
		long horasTotalesEsperadas = 34;
		
		// act
		long horasTotalesParqueoCalculadas = calculadoraTiempoParqueo.obtenerHorasParqueo(fechaIngreso,fechaSalida);
		
		// assert
		Assert.assertTrue(horasTotalesEsperadas == horasTotalesParqueoCalculadas);
	}

	@Test
	public void testObtenerDiasACobrar() {
		// arrange
		long horasTotalesDeParqueo = 34;
		long diasACobrarEsperados = 2;
		
		// act
		long diasACobrarCalculados = calculadoraTiempoParqueo.calcularDiasACobrar(horasTotalesDeParqueo);
		// assert
		Assert.assertTrue(diasACobrarEsperados == diasACobrarCalculados);
	}
	
	@Test
	public void testObtenerDiasACobrarCuandoDiasEsperadosSon0() {
		// arrange
		long horasTotalesDeParqueo = 4;
		long diasACobrarEsperados = 0;
		
		// act
		long diasACobrarCalculados = calculadoraTiempoParqueo.calcularDiasACobrar(horasTotalesDeParqueo);
		// assert
		Assert.assertTrue(diasACobrarEsperados == diasACobrarCalculados);
	}
	
	
	@Test
	public void testObtenerHorasACobrar() {
		// arrange
		long horasTotalesDeParqueo = 50;
		long horasACobrarEsperados = 2;
		
		// act
		long horasACobraroCalculadas = calculadoraTiempoParqueo.calcularHorasACobrar(horasTotalesDeParqueo); 
		// assert
		Assert.assertTrue(horasACobrarEsperados == horasACobraroCalculadas);
	}
	
	
	@Test
	public void testObtenerHorasACobrarCuandoHorasEsperadasSon0() {
		// arrange
		long horasTotalesDeParqueo = 58;
		long horasACobrarEsperados = 0;
		
		// act
		long horasACobraroCalculadas = calculadoraTiempoParqueo.calcularHorasACobrar(horasTotalesDeParqueo); 
		// assert
		Assert.assertTrue(horasACobrarEsperados == horasACobraroCalculadas);
	}
	
	@Test
	public void testObtenerHorasACobrarDiasDeParQueoACobar() {
		// arrange
		long horasTotalesDeParqueo = 4;
		long horasACobrarEsperados = 4;
		
		// act
		long horasACobraroCalculadas = calculadoraTiempoParqueo.calcularHorasACobrar(horasTotalesDeParqueo); 
		// assert
		Assert.assertTrue(horasACobrarEsperados == horasACobraroCalculadas);
	}
}
