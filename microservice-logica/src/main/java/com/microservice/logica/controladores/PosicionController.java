package com.microservice.logica.controladores;

import com.microservice.logica.entidades.Posicion;
import com.microservice.logica.excepciones.ErrorResponse;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.servicios.PosicionService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/posicion")
public class PosicionController {

    @Autowired
    private PosicionService posicionService;

<<<<<<< HEAD
    //Esto solo lo puede hacer o el usuario o el vehiculo
=======
    //TODO: Autorizado interesado que este usando en ese momento el vehiculo
>>>>>>> 463e71e6b6688012ce44f85fee4db040f1a67972
    @PostMapping("")
    public String crearUbicacionVehiculo(@RequestBody Posicion posicion) {
        posicionService.save(posicion);
        return "OK";
    }

    //Manejo de errores
    @ExceptionHandler(PruebaException.class)
    public ResponseEntity<ErrorResponse> handlePruebaException(PruebaException ex) {
        ErrorResponse respuesta = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

}
