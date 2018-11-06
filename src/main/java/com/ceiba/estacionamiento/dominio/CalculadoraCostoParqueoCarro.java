package com.ceiba.estacionamiento.dominio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;

@Component
public class CalculadoraCostoParqueoCarro implements CalculadoraCostoParqueo {

	@Autowired
	public CalculadoraTiempoParqueo calculadoraTiempo = new CalculadoraTiempoParqueo();

	@Override
	public double calcularCostoFactura(FacturaEntity factura) {
		long horasTotalesParqueo = calculadoraTiempo.obtenerHorasParqueo(factura.getFechaIngreso(),factura.getFechaSalida());
		int diasParqueo = calculadoraTiempo.calcularDiasTranscurridos(horasTotalesParqueo);
		int horasParqueo = calculadoraTiempo.calcularHorasTranscurridas(horasTotalesParqueo);
		
		return (diasParqueo*Constantes.COSTO_DIA_CARRO) + (horasParqueo*Constantes.COSTO_HORA_CARRO);
		}

}
