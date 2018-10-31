package com.ceiba.estacionamiento.util;


public final class  Constantes {
	
	public static final String PARQUEADERO_CEIBA = "Parqueadero Ceiba";
	
	public static final String VEHICULO_MOTO = "Moto";
	public static final int CODIGO_VEHICULO_MOTO = 1;
	public static final int PARQUEADERO_CEIBA_LIMITE_MOTOS = 10;
	public static final double COSTO_HORA_MOTO = 500;
	public static final double COSTO_DIA_MOTO = 4000;
	public static final double COBRO_ADICIONAL_MOTO_CC_MAYOR_A_500 = 2000;
	
	public static final String VEHICULO_CARRO = "Carro";
	public static final int CODIGO_VEHICULO_CARRO = 2;
	public static final int PARQUEADERO_CEIBA_LIMITE_CARROS = 20;
	public static final double COSTO_HORA_CARRO = 1000;
	public static final double COSTO_DIA_CARRO = 8000;
	
	public static final int HORAS_DIA = 24;
	public static final int HORAS_INICIO_COBRO_UN_DIA = 9;
	
	private Constantes() {}

}
