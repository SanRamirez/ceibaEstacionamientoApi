package com.ceiba.estacionamiento.dominio;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;

public interface CalculadoraCostoParqueo {
	public double calcularCostoFactura(FacturaEntity factura);
}
