package com.ceiba.estacionamiento.dominio;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CalculadoraTiempoParqueo {
	
	
	
	public CalculadoraTiempoParqueo() {
		super();
	}

	public  long obtenerHorasParqueo(Date fechaIngreso, Date fechaSalida){
		long fechaIngresoEnMilis = fechaIngreso.getTime();
		long fechaSalidaEnMilis = fechaSalida.getTime();
		long tiempoTranscurridoEnMilis = fechaSalidaEnMilis-fechaIngresoEnMilis;
		return convertirMiliEnHoras(tiempoTranscurridoEnMilis);
	}
	
	private  long convertirMiliEnHoras(long milisegundos) {		
		double horas = (double) milisegundos/(1000 * 60 * 60);
		return (long) Math.ceil(milisegundos/(horas));
	}
	
	public int calcularDiasTranscurridos(float horasTotalesParqueo) {
		int dias = 0;
		float diasTranscurridos = horasTotalesParqueo/24;
		if (diasTranscurridos > 0) {
			dias = (int) Math.floor(diasTranscurridos);
		}
		return dias;
	}
	
	public int calcularHorasTranscurridas(float horasTotalesParqueo) {
		int horas;
		float horasDelUltimoDia = horasTotalesParqueo % 24; 
		horas = (int) Math.ceil(horasDelUltimoDia);
		return horas;
	}

}
