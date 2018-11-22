package com.ceiba.estacionamiento.dominio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.util.Constantes;

@Component
public class CalculadoraCostoParqueoFactory {

	private static final Logger LOGGER = LogManager.getLogger(CalculadoraCostoParqueoFactory.class);

	private final CalculadoraCostoParqueo calculadoraCostoParqueoMoto = new CalculadoraCostoParqueoMoto();
	private final CalculadoraCostoParqueo calculadoraCostoParqueoCarro = new CalculadoraCostoParqueoCarro();

	public CalculadoraCostoParqueo getCalculadoraCostoParqueo(int tipoVehiculo) {
		switch (tipoVehiculo) {
			case Constantes.CODIGO_VEHICULO_MOTO:
				LOGGER.info("El factory retorna una calculadora de costos para motos");
				return calculadoraCostoParqueoMoto;
			case Constantes.CODIGO_VEHICULO_CARRO:
				LOGGER.info("El factory retorna una calculadora costos para carros");
				return calculadoraCostoParqueoCarro;
			default:
				return null;
		}
	}
}
