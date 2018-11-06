package com.ceiba.estacionamiento.persistencia.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ceiba.estacionamiento.modelo.Vehiculo;

@Entity
@Table(name="factura")
public class FacturaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	private String placaVehiculo;
	private int cilindrajeVehiculo;
	private int tipoVehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private double valor;
	private int totalDias;
	private int totalHoras;
	private boolean parqueado;
	
	public FacturaEntity(Vehiculo vehiculo, Date fechaIngreso) {
		super();
		this.placaVehiculo = vehiculo.getPlaca();
		this.cilindrajeVehiculo = vehiculo.getCilindraje();
		this.tipoVehiculo = vehiculo.getTipo();
		this.fechaIngreso = fechaIngreso;
	}
	
	public FacturaEntity(Vehiculo vehiculo, Date fechaIngreso, boolean parqueado) {
		super();
		this.placaVehiculo = vehiculo.getPlaca();
		this.cilindrajeVehiculo = vehiculo.getCilindraje();
		this.tipoVehiculo = vehiculo.getTipo();
		this.fechaIngreso = fechaIngreso;
		this.parqueado = parqueado;
	}
	
	public FacturaEntity() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vehiculo getVehiculo() {
		return new Vehiculo(placaVehiculo, cilindrajeVehiculo, tipoVehiculo);
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.placaVehiculo = vehiculo.getPlaca();
		this.cilindrajeVehiculo = vehiculo.getCilindraje();
		this.tipoVehiculo = vehiculo.getTipo();
	}
	
	
	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public int getCilindrajeVehiculo() {
		return cilindrajeVehiculo;
	}

	public void setCilindrajeVehiculo(int cilindrajeVehiculo) {
		this.cilindrajeVehiculo = cilindrajeVehiculo;
	}

	public int getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(int tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getTotalDias() {
		return totalDias;
	}

	public void setTotalDias(int totalDias) {
		this.totalDias = totalDias;
	}

	public int getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}

	public boolean isParqueado() {
		return parqueado;
	}

	public void setParqueado(boolean parqueado) {
		this.parqueado = parqueado;
	}

}
