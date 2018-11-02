package com.ceiba.estacionamiento.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.dominio.VigilanteParqueadero;
import com.ceiba.estacionamiento.exception.EstacionamientoException;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.util.validaciones.VehiculoValidacion;

@RestController
@RequestMapping("/api/estacionamiento")
public class EstacionamientoController {
	
	private static final Logger LOGGER = LogManager.getLogger(VehiculoValidacion.class);
	
	@Autowired
	FacturaDao facturaDao;
	@Autowired
	VigilanteParqueadero vigilanteParqueadero; 
	

	@PostMapping(value = "/registrarIngresoVehiculo")
	public ResponseEntity<String> ingresarVehiculo(@RequestBody Vehiculo vehiculo)
	{
		try {
			vigilanteParqueadero.ingresarVehiculo(vehiculo, new Date());
			return new ResponseEntity<>("OK", HttpStatus.OK);
		} catch (EstacionamientoException exception) {
			LOGGER.info("Error en /ingresarVehiculo",exception);
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
   @GetMapping("/obtenerSaludo/{id}")
   public String getsaludo(@PathVariable(value="id") String nombre){
	   vigilanteParqueadero.ingresarVehiculo(new Vehiculo("BTS-036", 250, 1), new Date());
	   return "hola "+nombre;
    }
   
   @GetMapping("/obtenerParqueados")
   public List<Vehiculo> getParqueados(){
	   return facturaDao.obtenerVehiculosParqueados();
    }
	 
   @GetMapping("/obtenerParqueadosPorPlaca/{placa}")
   public Vehiculo getParqueadoPorPlaca(@PathVariable(value="placa") String placa){
	   return facturaDao.obtenerVeiculoParqueadoPorPlaca(placa);
    }
   
   @GetMapping("/obtenerParqueadosPorTipo")
   public int getParqueadoPorTipo(){
	   return facturaDao.contarVehiculosParqueadosPorTipo(1);
    }
   
}
