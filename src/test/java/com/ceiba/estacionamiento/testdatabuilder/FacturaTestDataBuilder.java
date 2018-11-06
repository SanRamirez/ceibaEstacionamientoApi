package com.ceiba.estacionamiento.testdatabuilder;

import java.util.Date;

import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;

public class FacturaTestDataBuilder {

	private static final String PLACA_VEHICULO = "BTS-036";
	private static final int  CILINDRAJE_VEHICULO = 250 ;
	private static final int TIPO_VEHICULO = 1;
	private static final Date FECHA_INGRESO = new Date();
	
	private String placa;
	private int cilindraje;
	private int tipo;

	private String placaVehiculo;
	private int cilindrajeVehiculo;
	private int tipoVehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private double valor;
	private int totalDias;
	private int totalHoras;
	private boolean parqueado;
	
	public  FacturaTestDataBuilder() {
		this.placaVehiculo = PLACA_VEHICULO;
		this.cilindrajeVehiculo = CILINDRAJE_VEHICULO;
		this.tipoVehiculo = TIPO_VEHICULO;
		this.fechaIngreso = FECHA_INGRESO;
	}
	
	public FacturaTestDataBuilder conVechiculoYFechaIngreso(Vehiculo vehiculo) {
		this.placa = vehiculo.getPlaca();
		this.cilindraje = vehiculo.getCilindraje();
		this.tipo = vehiculo.getTipo();
		return this;
	}
	

	
	public FacturaEntity build() {
		return new FacturaEntity(new Vehiculo(this.placaVehiculo, this.cilindrajeVehiculo,this.tipoVehiculo),FECHA_INGRESO);
	}
	

}
