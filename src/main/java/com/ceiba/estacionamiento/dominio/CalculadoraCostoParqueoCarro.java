package com.ceiba.estacionamiento.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;

@Component
public class CalculadoraCostoParqueoCarro implements CalculadoraCostoParqueo {

	@Autowired
	private CalculadoraTiempoParqueo calculadoraTiempo = new CalculadoraTiempoParqueo();
	
	@Override
	public FacturaEntity calcularCostoFactura(FacturaEntity factura) {
		long horasTotalesParqueo = calculadoraTiempo.obtenerHorasParqueo(factura.getFechaIngreso(),factura.getFechaSalida());
		int diasACobrar = calculadoraTiempo.calcularDiasACobrar(horasTotalesParqueo);
		int horasACobrar = calculadoraTiempo.calcularHorasACobrar(horasTotalesParqueo);
		double costoParqueo = (diasACobrar*Constantes.COSTO_DIA_CARRO) + (horasACobrar*Constantes.COSTO_HORA_CARRO);
		factura.setTotalDias(diasACobrar);
		factura.setTotalHoras(horasACobrar);
		factura.setValor(costoParqueo);
		return factura;
	}

}