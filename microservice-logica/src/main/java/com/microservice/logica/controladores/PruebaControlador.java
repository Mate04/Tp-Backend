package com.microservice.logica.controladores;

import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.entidades.Prueba;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.ErrorResponse;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.servicios.InteresadoServicioImpl;
import com.microservice.logica.servicios.PruebaService;
import com.microservice.logica.servicios.VehiculoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prueba")
public class PruebaControlador {
    @Autowired
    private InteresadoServicioImpl interesadoServicio;
    @Autowired
    private VehiculoServiceImp vehiculoServico;
    @Autowired
    private PruebaService pruebaServicio;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPrueba(@RequestBody Prueba prueba) {
        if (prueba.getInteresado() == null) {
            throw new PruebaException("El interesado es obligatorio");
        }
        Interesado interesadoTraido = interesadoServicio.findByID(prueba.getInteresado().getDocumento());

        if (interesadoTraido.isRestringido()) {
            throw new PruebaException("El interesado esta restringido");
        }
        if (!interesadoTraido.validarLicencia()){
            throw new PruebaException("El interesado no tiene licencia valida");
        }
        Vehiculo vehiculo = vehiculoServico.findByID(prueba.getVehiculo().getId());
        if (!vehiculo.isDisponible()) {
            throw new PruebaException("El vehiculo no esta disponible");
        }
        if (interesadoTraido.getPruebas().) {
            throw new PruebaException("El interesado ya esta haciendo una prueba en curso");
        }

        vehiculo.setDisponible(false);
        vehiculoServico.save(vehiculo);
        pruebaServicio.save(prueba);

     }

    @ExceptionHandler(PruebaException.class)
    public ResponseEntity<ErrorResponse> handlePruebaException(PruebaException ex) {
        ErrorResponse respuesta = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
     }
}

