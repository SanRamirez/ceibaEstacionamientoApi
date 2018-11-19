package com.ceiba.estacionamiento.dominio;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.util.Constantes;

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
	
	
	public int calcularDiasACobrar(float horasTotalesParqueo){
		int diasAcobrar = calcularDiasTranscurridos(horasTotalesParqueo);
		float horasDelUltimoDia = horasTotalesParqueo % Constantes.HORAS_DIA; 
		
		if (horasDelUltimoDia > Constantes.HORAS_INICIO_COBRO_UN_DIA) {
			diasAcobrar += 1;
		}
		
		return diasAcobrar;
	}
	
	public int calcularHorasACobrar(float horasTotalesParqueo){
		int horasAcobrar = calcularHorasTranscurridas(horasTotalesParqueo);
		
		if (horasAcobrar > Constantes.HORAS_INICIO_COBRO_UN_DIA) {
			horasAcobrar = 0;
		}
		
		return horasAcobrar;
	}
	
	
	private int calcularDiasTranscurridos(float horasTotalesParqueo) {
		int dias = 0;
		float diasTranscurridos = horasTotalesParqueo / Constantes.HORAS_DIA;
		if (diasTranscurridos > 0) {
			dias = (int) Math.floor(diasTranscurridos);
		}
		return dias;
	}
	
	private int calcularHorasTranscurridas(float horasTotalesParqueo) {
		int horas = 0;
		float horasDelUltimoDia = horasTotalesParqueo % Constantes.HORAS_DIA; 
		horas = (int) Math.ceil(horasDelUltimoDia);
		return horas;
	}

}
