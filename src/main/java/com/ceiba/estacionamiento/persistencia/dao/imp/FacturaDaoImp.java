package com.ceiba.estacionamiento.persistencia.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.persistencia.repository.FacturaRepository;

@Service
public class FacturaDaoImp implements FacturaDao {
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Override
	public void guardarFactura(FacturaEntity factura) {
			facturaRepository.save(factura);
	}
	
	@Override
	public Vehiculo obtenerVeiculoParqueadoPorPlaca(String placaVehiculo) {
		boolean estaParqueado = true;
		FacturaEntity facturaEntity = facturaRepository.findByPlacaVehiculoAndParqueado(placaVehiculo, estaParqueado);
		return (facturaEntity != null ? facturaEntity.getVehiculo() : null);
	}

	@Override
	public void actualizarFactura(FacturaEntity factura) {
		guardarFactura(factura);
	}

	@Override
	public List<Vehiculo> obtenerVehiculosPorTipoParqueados(int tipoVehiculo) {
		List<Vehiculo> vehiculosDeTipoEspecifico = new ArrayList<>();
		List<FacturaEntity> facturasVehiculosDeTipoEspecifico = facturaRepository.findByTipoVehiculo(tipoVehiculo);
		
		if (facturasVehiculosDeTipoEspecifico == null || facturasVehiculosDeTipoEspecifico.isEmpty()) {
			return Collections.emptyList();
		}else {
			facturasVehiculosDeTipoEspecifico.forEach(factura ->  vehiculosDeTipoEspecifico.add(factura.getVehiculo()));
			return vehiculosDeTipoEspecifico;
		}
	}

	@Override
	public List<Vehiculo> obtenerVehiculosParqueados() {
		List<Vehiculo> vehiculosParqueados = new ArrayList<>();
		List<FacturaEntity> facturasVehiculosParqueados = facturaRepository.findByParqueado(true);
		
		if (facturasVehiculosParqueados == null || facturasVehiculosParqueados.isEmpty()) {
			return Collections.emptyList();
		}else {
			facturasVehiculosParqueados.forEach(factura ->  vehiculosParqueados.add(factura.getVehiculo()));
			return vehiculosParqueados;
		}
	}

	@Override
	public int contarVehiculosParqueadosPorTipo(int tipoVehiculo) {
		return facturaRepository.countByTipoVehiculoAndParqueado(tipoVehiculo, true);
	}

	@Override
	public FacturaEntity obtenerFacturaVeiculoParqueadoPorPlaca(String placa) {
		boolean estaParqueado = true;
		FacturaEntity facturaEntity = facturaRepository.findByPlacaVehiculoAndParqueado(placa, estaParqueado);
		return (facturaEntity != null ? facturaEntity : null);
	}

}
