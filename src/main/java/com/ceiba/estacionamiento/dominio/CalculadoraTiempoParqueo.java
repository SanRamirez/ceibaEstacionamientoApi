package com.ceiba.estacionamiento.dominio;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CalculadoraTiempoParqueo {
	
	public CalculadoraTiempoParqueo() {
		super();
	}

	public  long obtenerHorasParqueo(Date fechaIngreso, Date fechaSalida){
		long horasTotales;
		long fechaIngresoEnMilis = fechaIngreso.getTime();
		long fechaSalidaEnMilis = fechaSalida.getTime();
		long tiempoTranscurridoEnMilis = fechaSalidaEnMilis-fechaIngresoEnMilis;
		horasTotales = convertirMiliEnHoras(tiempoTranscurridoEnMilis);
		return horasTotales;
	}
	
	private  long convertirMiliEnHoras(long milisegundos) {		
		double horas = (double) milisegundos/(1000 * 60 * 60);
		return (long) Math.ceil(horas);
	}
	
	
	public int calcularDiasAcobrar(float horasTotalesParqueo){
		int diasAcobrar = calcularDiasTranscurridos(horasTotalesParqueo);
		float horasDelUltimoDia = horasTotalesParqueo % 24; 
		
		if (horasDelUltimoDia > 9) {
			diasAcobrar += 1;
		}
		
		return diasAcobrar;
	}
	
	public int calcularHorasAcobrar(float horasTotalesParqueo){
		int horasAcobrar = calcularHorasTranscurridas(horasTotalesParqueo);
		
		if (horasAcobrar > 9) {
			horasAcobrar = 0;
		}
		
		return horasAcobrar;
	}
	
	
	private int calcularDiasTranscurridos(float horasTotalesParqueo) {
		int dias = 0;
		float diasTranscurridos = horasTotalesParqueo/24;
		if (diasTranscurridos > 0) {
			dias = (int) Math.floor(diasTranscurridos);
		}
		return dias;
	}
	
	private int calcularHorasTranscurridas(float horasTotalesParqueo) {
		int horas = 0;
		float horasDelUltimoDia = horasTotalesParqueo % 24; 
		horas = (int) Math.ceil(horasDelUltimoDia);
		return horas;
	}

}
