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

import com.ceiba.estacionamiento.dominio.CalculadoraCostoParqueoCarro;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.testdatabuilder.FacturaTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.estacionamiento.util.Constantes;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculadoraCostoParqueoCarroTest {
	
	@Autowired
	public CalculadoraCostoParqueoCarro calculadoraCostocarro;

	@Test
	public void testCalcularCostoFactura() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(Constantes.CODIGO_VEHICULO_CARRO).build();
		Calendar calendar = new GregorianCalendar(2018,Calendar.NOVEMBER,1,8,0,0);
		Date fechaIngreso = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.HOUR, 3);
		Date fechaSalida = calendar.getTime();
		FacturaEntity factura = new FacturaTestDataBuilder().conVechiculoYFechaIngreso(vehiculo, fechaIngreso).build();
		factura.setFechaSalida(fechaSalida);
		double costoEsperado = 11000;
		
		// act
		double costoCalculado = calculadoraCostocarro.calcularCostoFactura(factura).getValor();

		// assert
		Assert.assertTrue(costoEsperado == costoCalculado);
	}

}
