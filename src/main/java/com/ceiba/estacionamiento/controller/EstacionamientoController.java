package com.ceiba.estacionamiento.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.dominio.VigilanteParqueadero;
import com.ceiba.estacionamiento.exception.EstacionamientoException;
import com.ceiba.estacionamiento.modelo.Factura;
import com.ceiba.estacionamiento.modelo.MensajeRespuesta;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.modelo.VehiculoIngresado;

@RestController
@RequestMapping("/api/v1.0/estacionamiento")
public class EstacionamientoController {
		
	private static final Logger LOGGER = LogManager.getLogger(EstacionamientoController.class);
	
	@Autowired
	VigilanteParqueadero vigilanteParqueadero; 

	
	@CrossOrigin
	@PostMapping(value = "/registrarIngresoVehiculo")
	public ResponseEntity<MensajeRespuesta> ingresarVehiculo(@RequestBody Vehiculo vehiculo)
	{
		try {
			vigilanteParqueadero.ingresarVehiculo(vehiculo);
			return new ResponseEntity<>(new MensajeRespuesta("OK"), HttpStatus.CREATED);
		} catch (EstacionamientoException exception) {
			LOGGER.info("Error en /registrarIngresoVehiculo",exception);
			return new ResponseEntity<>(new MensajeRespuesta(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin
	@GetMapping(value = "/registrarSalidaVehiculo/{placa}")
	public ResponseEntity<Object> retirarVehiculo(@PathVariable(value="placa") String placa){
		Factura factura;
		try {
			factura = vigilanteParqueadero.registrarSalidaVehiculo(placa);
			return new ResponseEntity<>(factura, HttpStatus.OK);
		} catch (EstacionamientoException exception) {
			LOGGER.info("Error en /registrarSalidaVehiculo",exception);
			return new ResponseEntity<>(new MensajeRespuesta(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@GetMapping(value = "/consultarVehiculos")
	public ResponseEntity<List<VehiculoIngresado>> obtenerVehiculosParqueados(){ 
		return new ResponseEntity<>(vigilanteParqueadero.obtenerVehiculosParqueados(), HttpStatus.OK );
	}

}
