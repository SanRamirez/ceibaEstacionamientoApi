package com.ceiba.estacionamiento.dominio.builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ceiba.estacionamiento.modelo.Factura;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.Constantes;

public final class FacturaBuilder {
	
	private static final String FORMATO_FECHA = "yyyy-MM-dd hh:mm a";
	
	
	private FacturaBuilder() {}
	
	public static Factura convertirADominio(FacturaEntity facturaEntity ) {
		Factura factura = null;

		if(facturaEntity != null) {
			factura = new Factura();
			DateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			String tipoVehiculo = "";
			
			factura.setPlacaVehiculo(facturaEntity.getPlacaVehiculo());
			factura.setCilindrajeVehiculo( Integer.toString(facturaEntity.getCilindrajeVehiculo()) );
			if(facturaEntity.getTipoVehiculo() == 1) {
				tipoVehiculo = Constantes.VEHICULO_MOTO;
			}else if (facturaEntity.getTipoVehiculo() == 2) {
				tipoVehiculo = Constantes.VEHICULO_CARRO;
			}
			factura.setTipoVehiculo(tipoVehiculo);
			factura.setFechaIngreso(dateFormat.format(facturaEntity.getFechaIngreso()));
			factura.setFechaSalida(dateFormat.format(facturaEntity.getFechaSalida()));
			factura.setValor(String.valueOf(facturaEntity.getValor()));
			factura.setTotalHoras(Integer.toString(facturaEntity.getTotalHoras()));
			factura.setTotalDias(Integer.toString(facturaEntity.getTotalDias()));
		}
		
		return factura;
	}

}
