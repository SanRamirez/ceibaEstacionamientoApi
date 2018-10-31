package com.ceiba.estacionamiento.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;

@RestController
@RequestMapping("/api/estacionamiento")
public class EstacionamientoController {
	
	@Autowired
	FacturaDao factDao;
	
	FacturaEntity facturap = new FacturaEntity(new Vehiculo("BTS-036", 250, 1), new Date());
	
   @GetMapping("/obtenerSaludo/{id}")
   public String getsaludo(@PathVariable(value="id") String nombre){
	   facturap.setParqueado(true);
	   factDao.guardarFactura(facturap);
	   return "hola "+nombre;
    }
   
   @GetMapping("/obtenerParqueados")
   public List<Vehiculo> getParqueados(){
	   return factDao.obtenerVehiculosParqueados();
    }
	 
   @GetMapping("/obtenerParqueadoPorPlaca/{placa}")
   public Vehiculo getParqueadoPorPlaca(@PathVariable(value="placa") String placa){
	   return factDao.obtenerVeiculoParqueadoPorPlaca(placa);
    }
   
   @GetMapping("/obtenerParqueadoPorTipo")
   public int getParqueadoPorTipo(){
	   return factDao.contarVehiculosParqueadosPorTipo(1);
    }
   
}
