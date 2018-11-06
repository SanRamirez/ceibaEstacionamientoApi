package com.ceiba.estacionamiento.testdatabuilder;

import com.ceiba.estacionamiento.modelo.Vehiculo;

public class VehiculoTestDataBuilder {
	
	private static final String PLACA = "BTS-036";
	private static final int  CILINDRAJE = 250 ;
	private static final int TIPO = 1;

	
	
	private String placa;
	private int cilindraje;
	private int tipo;

	public  VehiculoTestDataBuilder() {
		this.placa = PLACA;
		this.cilindraje = CILINDRAJE;
		this.tipo = TIPO;
	}
	
	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}
	
	public VehiculoTestDataBuilder conTipo(int tipo) {
		this.tipo = tipo;
		return this;
	}
	
	public Vehiculo build() {
		return new Vehiculo(this.placa, this.cilindraje,this.tipo);
	}

}
