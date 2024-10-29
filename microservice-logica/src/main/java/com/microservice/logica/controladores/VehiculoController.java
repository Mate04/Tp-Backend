package com.microservice.logica.controladores;

import com.microservice.logica.controladores.DTO.DTOVehiculo;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.ErrorResponse;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.servicios.VehiculoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

    @Autowired
    VehiculoServiceImp vehiculoServiceImp;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        vehiculoServiceImp.save(vehiculo);
        return new ResponseEntity<>("Se creo correctamente el vehiculo", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DTOVehiculo>> findAllVehiculos() {
        List<DTOVehiculo> vehiculos = vehiculoServiceImp.findAll()
                .stream()
                .map(DTOVehiculo::new)
                .toList();
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    //manejo de error
    @ExceptionHandler(PruebaException.class)
    public ResponseEntity<ErrorResponse> handlePruebaException(PruebaException ex) {
        ErrorResponse respuesta = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
