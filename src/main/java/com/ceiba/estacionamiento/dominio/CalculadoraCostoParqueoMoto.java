package com.ceiba.estacionamiento.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;

@Component
public class CalculadoraCostoParqueoMoto implements CalculadoraCostoParqueo {

	@Autowired
	private CalculadoraTiempoParqueo calculadoraTiempo = new CalculadoraTiempoParqueo();
	
	@Override
	public FacturaEntity calcularCostoFactura(FacturaEntity factura) {
		long horasTotalesParqueo = calculadoraTiempo.obtenerHorasParqueo(factura.getFechaIngreso(),factura.getFechaSalida());
		int diasACobrar = calculadoraTiempo.calcularDiasAcobrar(horasTotalesParqueo);
		int horasACobrar = calculadoraTiempo.calcularHorasAcobrar(horasTotalesParqueo);
		double costoParqueo;
		if(factura.getCilindrajeVehiculo() > 500) {
			costoParqueo = ((diasACobrar*Constantes.COSTO_DIA_MOTO) + (horasACobrar*Constantes.COSTO_HORA_MOTO))
					+ Constantes.COBRO_ADICIONAL_MOTO_CC_MAYOR_A_500;
		}else {
			costoParqueo = (diasACobrar*Constantes.COSTO_DIA_MOTO) + (horasACobrar*Constantes.COSTO_HORA_MOTO);
		}
		factura.setTotalDias(diasACobrar);
		factura.setTotalHoras(horasACobrar);
		factura.setValor(costoParqueo);
		return factura;
	}

}


/*
	@Override
	public FacturaEntity calcularCostoFactura(FacturaEntity factura) {
		long horasTotalesParqueo = calculadoraTiempo.obtenerHorasParqueo(factura.getFechaIngreso(),factura.getFechaSalida());
		int diasACobrar = calculadoraTiempo.calcularDiasAcobrar(horasTotalesParqueo);
		int horasACobrar = calculadoraTiempo.calcularHorasAcobrar(horasTotalesParqueo);
		double costoParqueo; 
		if(factura.getCilindrajeVehiculo() > 500 ){
			costoParqueo = ((diasACobrar*Constantes.COSTO_DIA_MOTO) + (horasACobrar*Constantes.COSTO_HORA_MOTO))
					+ Constantes.COBRO_ADICIONAL_MOTO_CC_MAYOR_A_500;
		}else {
			costoParqueo = (diasACobrar*Constantes.COSTO_DIA_MOTO) + (horasACobrar*Constantes.COSTO_HORA_MOTO);
		}
		factura.setTotalDias(diasACobrar);
		factura.setTotalHoras(horasACobrar);
		factura.setValor(costoParqueo);
		return factura;
	}

*/