package com.ceiba.estacionamiento.persistencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {

	public abstract FacturaEntity findById(int id);
	public abstract List<FacturaEntity> findByParqueado(boolean estaParqueado);
	public abstract List<FacturaEntity> findByTipoVehiculo(int tipoVehiculo);
	public abstract FacturaEntity findByPlacaVehiculoAndParqueado(String placaVehiculo,boolean estaParqueado);
	public abstract int countByTipoVehiculoAndParqueado(int tipoVehiculo,boolean estaParqueado);
}
