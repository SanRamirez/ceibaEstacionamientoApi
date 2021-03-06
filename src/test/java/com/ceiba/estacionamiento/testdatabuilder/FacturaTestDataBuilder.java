package com.ceiba.estacionamiento.testdatabuilder;

import java.util.Date;

import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;

public class FacturaTestDataBuilder {

	private static final String PLACA_VEHICULO = "BTS-036";
	private static final int  CILINDRAJE_VEHICULO = 250 ;
	private static final int TIPO_VEHICULO = 1;
	private static final Date FECHA_INGRESO = new Date();
	
	private String placaVehiculo;
	private int cilindrajeVehiculo;
	private int tipoVehiculo;
	private Date fechaIngreso;
	
	public  FacturaTestDataBuilder() {
		this.placaVehiculo = PLACA_VEHICULO;
		this.cilindrajeVehiculo = CILINDRAJE_VEHICULO;
		this.tipoVehiculo = TIPO_VEHICULO;
		this.fechaIngreso = FECHA_INGRESO;
	}
	
	public FacturaTestDataBuilder conVechiculoYFechaIngreso(Vehiculo vehiculo, Date fechaIngreso) {
		this.placaVehiculo = vehiculo.getPlaca();
		this.cilindrajeVehiculo = vehiculo.getCilindraje();
		this.tipoVehiculo = vehiculo.getTipo();
		this.fechaIngreso = fechaIngreso;
		return this;
	}
	
	public FacturaTestDataBuilder conPlaca(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
		return this;
	}
		
	public FacturaEntity build() {
		FacturaEntity factura = new FacturaEntity();
		factura.setPlacaVehiculo(this.placaVehiculo);
		factura.setCilindrajeVehiculo(this.cilindrajeVehiculo);
		factura.setTipoVehiculo(this.tipoVehiculo);
		factura.setFechaIngreso(this.fechaIngreso);
		return factura;
	}
	
}
