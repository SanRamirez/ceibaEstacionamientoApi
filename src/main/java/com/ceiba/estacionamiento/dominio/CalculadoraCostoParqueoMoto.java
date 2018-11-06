package com.ceiba.estacionamiento.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;

@Component
public class CalculadoraCostoParqueoMoto implements CalculadoraCostoParqueo {

	@Autowired
	public CalculadoraTiempoParqueo calculadoraTiempo;
	
	@Override
	public double calcularCostoFactura(FacturaEntity factura) {
		long horasTotalesParqueo = calculadoraTiempo.obtenerHorasParqueo(factura.getFechaIngreso(),factura.getFechaSalida());
		int diasParqueo = calculadoraTiempo.calcularDiasTranscurridos(horasTotalesParqueo);
		int horasParqueo = calculadoraTiempo.calcularHorasTranscurridas(horasTotalesParqueo);
		double costoSencillo = (diasParqueo*Constantes.COSTO_DIA_MOTO) + (horasParqueo*Constantes.COSTO_HORA_MOTO);
		
		return (factura.getCilindrajeVehiculo() > 500 ? costoSencillo : costoSencillo+ Constantes.COBRO_ADICIONAL_MOTO_CC_MAYOR_A_500);

	}

}
