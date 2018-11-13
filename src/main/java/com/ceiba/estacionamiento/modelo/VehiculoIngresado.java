package com.ceiba.estacionamiento.modelo;

import java.util.Date;

public class VehiculoIngresado extends Vehiculo {
	
	private Date fechaIngreso;
	
	public VehiculoIngresado() {
	}

	public VehiculoIngresado(String placa, int cilindraje, int tipo, Date fechaIngreso) {
		super(placa,cilindraje,tipo);
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	

}
