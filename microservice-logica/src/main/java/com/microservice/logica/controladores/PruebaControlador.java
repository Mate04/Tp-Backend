package com.microservice.logica.controladores;

import com.microservice.logica.controladores.DTO.DTOComentario;
import com.microservice.logica.controladores.DTO.DTOPrueba;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        if (prueba.getEmpleado() == null) {
            throw new PruebaException("Debe ser asignado a un empleado la prueba");
        } if (prueba.getInteresado() == null) {
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
        if (interesadoTraido.tienePruebaEnCurso()) {
            throw new PruebaException("El interesado ya esta haciendo una prueba en curso");
        }

        vehiculo.setDisponible(false);
        vehiculoServico.update(vehiculo);
        pruebaServicio.save(prueba);

     }

     @GetMapping
     @RequestMapping("/pruebas-en-curso")
     public List<DTOPrueba> obtenerPruebasEnCurso() {
        List<Prueba> pruebasEnCurso = pruebaServicio.buscarPruebasEnCurso();
        List<DTOPrueba> pruebasEnCursoDTO = new ArrayList<>();
        for (Prueba prueba : pruebasEnCurso) {
            pruebasEnCursoDTO.add(new DTOPrueba(prueba));
        }
        return pruebasEnCursoDTO;
     }




     @PatchMapping("finalizar/{id}")
     public ResponseEntity<DTOPrueba> finalizarPrueba(@PathVariable Long id, @RequestBody(required = false) DTOComentario comentario) {
        Prueba prueba = pruebaServicio.findByID(id);
        Vehiculo vehiculo = vehiculoServico.findByID(prueba.getVehiculo().getId());
        if (prueba.getFechaFin() != null) {
            throw new PruebaException("La prueba ya esta finalizada");
        }
        prueba.setFechaFin(LocalDateTime.now());


         if (comentario != null) {
             prueba.setComentario(comentario.getComentario());
         }

        vehiculo.setDisponible(true);
        vehiculoServico.update(vehiculo);
        pruebaServicio.save(prueba);
        return new ResponseEntity<>(new DTOPrueba(prueba), HttpStatus.OK);
     }

    @ExceptionHandler(PruebaException.class)
    public ResponseEntity<ErrorResponse> handlePruebaException(PruebaException ex) {
        ErrorResponse respuesta = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
     }
}

