package com.ceiba.estacionamiento.controller;

import java.rmi.RemoteException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.dominio.VigilanteParqueadero;
import com.ceiba.estacionamiento.exception.EstacionamientoException;
import com.ceiba.estacionamiento.modelo.Mensaje;
import com.ceiba.estacionamiento.modelo.Vehiculo;
import com.ceiba.estacionamiento.modelo.VehiculoIngresado;
import com.ceiba.estacionamiento.persistencia.dao.FacturaDao;
import com.ceiba.estacionamiento.persistencia.entity.FacturaEntity;
import com.ceiba.estacionamiento.util.validaciones.VehiculoValidacion;

import com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterface;
import com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterfaceProxy;
import com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;


@RestController
@RequestMapping("/api/v1.0/estacionamiento")
public class EstacionamientoController {
	
	
	private static final Logger LOGGER = LogManager.getLogger(VehiculoValidacion.class);
	
	@Autowired
	FacturaDao facturaDao;
	@Autowired
	VigilanteParqueadero vigilanteParqueadero; 

	
	@CrossOrigin
	@PostMapping(value = "/registrarIngresoVehiculo")
	public ResponseEntity<Mensaje> ingresarVehiculo(@RequestBody Vehiculo vehiculo)
	{
		try {
			vigilanteParqueadero.ingresarVehiculo(vehiculo, new Date());
			return new ResponseEntity<>(new Mensaje("OK"), HttpStatus.OK);
		} catch (EstacionamientoException exception) {
			LOGGER.info("Error en /registrarIngresoVehiculo",exception);
			return new ResponseEntity<>(new Mensaje(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin
	@GetMapping(value = "/registrarSalidaVehiculo/{placa}")
	public ResponseEntity<Object> retirarVehiculo(@PathVariable(value="placa") String placa){
		FacturaEntity factura;
		try {
			factura = vigilanteParqueadero.registrarSalidaVehiculo(placa);
			return new ResponseEntity<>(factura, HttpStatus.OK);
		} catch (EstacionamientoException exception) {
			LOGGER.info("Error en /registrarSalidaVehiculo",exception);
			return new ResponseEntity<>(new Mensaje(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@GetMapping(value = "/consultarVehiculos")
	public ResponseEntity<List<VehiculoIngresado>> obtenerVehiculosParqueados(){ 
		return new ResponseEntity<>(vigilanteParqueadero.obtenerVehiculosParqueados(), HttpStatus.OK );
	}
   
	@CrossOrigin
	@GetMapping(value = "/trm")
	@ResponseBody
	public  float  trm()  {
		TCRMServicesInterface proxy = new TCRMServicesInterfaceProxy("https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL");
        TcrmResponse tcrmResponse;
        try {
            tcrmResponse = proxy.queryTCRM(null);  
            if (tcrmResponse != null) {
                return tcrmResponse.getValue();
            }
        } catch (RemoteException e) {
        	LOGGER.info("Error en /trm",e);
		}
        
        return 0.0f;
	}
}
