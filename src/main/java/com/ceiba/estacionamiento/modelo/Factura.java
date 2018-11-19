package com.ceiba.estacionamiento.modelo;

public class Factura {

	private String placaVehiculo;
	private String cilindrajeVehiculo;
	private String tipoVehiculo;
	private String fechaIngreso;
	private String fechaSalida;
	private String valor;
	private String totalDiasACobrar;
	private String totalHorasACobrar;
	
	public Factura() {
		super();
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getCilindrajeVehiculo() {
		return cilindrajeVehiculo;
	}

	public void setCilindrajeVehiculo(String cilindrajeVehiculo) {
		this.cilindrajeVehiculo = cilindrajeVehiculo;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTotalDias() {
		return totalDiasACobrar;
	}

	public void setTotalDias(String totalDias) {
		this.totalDiasACobrar = totalDias;
	}

	public String getTotalHoras() {
		return totalHorasACobrar;
	}

	public void setTotalHoras(String totalHoras) {
		this.totalHorasACobrar = totalHoras;
	}

	@Override
	public String toString() {
		return "Factura [placaVehiculo=" + placaVehiculo + ", cilindrajeVehiculo=" + cilindrajeVehiculo
				+ ", tipoVehiculo=" + tipoVehiculo + ", fechaIngreso=" + fechaIngreso + ", fechaSalida=" + fechaSalida
				+ ", valor=" + valor + ", totalDiasACobrar=" + totalDiasACobrar + ", totalHorasACobrar=" + totalHorasACobrar + "]";
	}
	
	
}
