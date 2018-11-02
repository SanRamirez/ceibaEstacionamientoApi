package com.ceiba.estacionamiento.modelo;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;

public interface CalculadoraCostoFacturaParqueo {
	public double calcularCosto(FacturaEntity factura);
}
