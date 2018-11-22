package com.ceiba.estacionamiento.dominio.unitaria;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento.dominio.CalculadoraCostoParqueo;
import com.ceiba.estacionamiento.dominio.CalculadoraCostoParqueoCarro;
import com.ceiba.estacionamiento.dominio.CalculadoraCostoParqueoFactory;
import com.ceiba.estacionamiento.dominio.CalculadoraCostoParqueoMoto;
import com.ceiba.estacionamiento.util.Constantes;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculadoraCostoParqueoFactoryTest {
	
	@Autowired
	private CalculadoraCostoParqueoFactory calculadoraCostoParqueoFactory;
	
	@Test
	public void  getCalculadoraCostoDeTipoMoto() {
		// arrange
		CalculadoraCostoParqueo calculadoraCosto;
		
		// act  
		calculadoraCosto = calculadoraCostoParqueoFactory.getCalculadoraCostoParqueo(Constantes.CODIGO_VEHICULO_MOTO);
		
		// assert
		Assert.assertTrue(calculadoraCosto instanceof CalculadoraCostoParqueoMoto);
	}
	
	@Test
	public void  getCalculadoraCostoDeTipoCarro() {
		// arrange
		CalculadoraCostoParqueo calculadoraCosto;
		
		// act  
		calculadoraCosto = calculadoraCostoParqueoFactory.getCalculadoraCostoParqueo(Constantes.CODIGO_VEHICULO_CARRO);
		
		// assert
		Assert.assertTrue(calculadoraCosto instanceof CalculadoraCostoParqueoCarro);
	}
	
	@Test
	public void  getCalculadoraCostoDeTipoNoValido() {
		// arrange
		CalculadoraCostoParqueo calculadoraCosto;
		int tipoNoExistente = 100;
		
		// act  
		calculadoraCosto = calculadoraCostoParqueoFactory.getCalculadoraCostoParqueo(tipoNoExistente);
		
		// assert
		Assert.assertTrue(calculadoraCosto == null);
	}

}
