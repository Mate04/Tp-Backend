package com.microservice.logica.controladores;

import com.microservice.logica.controladores.DTO.DTOComentario;
import com.microservice.logica.controladores.DTO.DTOPrueba;
import com.microservice.logica.entidades.Prueba;
import com.microservice.logica.excepciones.ErrorResponse;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.servicios.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/prueba")
public class PruebaControlador {
    @Autowired
    private PruebaService pruebaServicio;

    //todo: autorizado empleado
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //Crear prueba es para los empleados
    public ResponseEntity<DTOPrueba> crearPrueba(@RequestBody Prueba prueba) {
        // aca verfico que se envie bien los mensajes
        if (prueba.getEmpleado() == null) {
            throw new PruebaException("Debe ser asignado a un empleado la prueba");
        } if (prueba.getInteresado() == null) {
            throw new PruebaException("El interesado es obligatorio");
        }
        pruebaServicio.save(prueba);
        Prueba pruebaCreada = pruebaServicio.findByID(prueba.getId());
        return  new ResponseEntity<>(new DTOPrueba(pruebaCreada),HttpStatus.CREATED);
     }
<<<<<<< HEAD
    //El admin puede ver las pruebas en curso
=======
     //TODO: authorizado admin
>>>>>>> 463e71e6b6688012ce44f85fee4db040f1a67972
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

<<<<<<< HEAD
    //Forma parte de los reportes del admin
=======
    //todo: autorizado admin
>>>>>>> 463e71e6b6688012ce44f85fee4db040f1a67972
    @RequestMapping("/pruebas-incidentes")
    public List<DTOPrueba> buscarPruebasConIncidentes() {
        List<Prueba> pruebasConIncidentes = pruebaServicio.buscarPruebasConIncidentes();
        List<DTOPrueba> pruebasConIncidentesDTO = new ArrayList<>();
        for (Prueba prueba : pruebasConIncidentes) {
            pruebasConIncidentesDTO.add(new DTOPrueba(prueba));
        }
        return pruebasConIncidentesDTO;
    }
<<<<<<< HEAD
    //Reporte del admin
=======

    //todo: autorizado admin
>>>>>>> 463e71e6b6688012ce44f85fee4db040f1a67972
    @RequestMapping("/pruebasVehiculo/{id_vehiculo}")
    public List<DTOPrueba> buscarPruebasXVehiculo(@PathVariable Long id_vehiculo) {
        List<Prueba> pruebasXVehiculo = pruebaServicio.buscarPruebasXVehiculo(id_vehiculo);
        List<DTOPrueba> pruebasXVehiculoDTO = new ArrayList<>();
        for (Prueba prueba : pruebasXVehiculo){
            pruebasXVehiculoDTO.add(new DTOPrueba(prueba));
        }
        return pruebasXVehiculoDTO;
    }
<<<<<<< HEAD

    //El empleado y el admin pueden finalizar pruebas
=======
    //todo: autorizado empleado
>>>>>>> 463e71e6b6688012ce44f85fee4db040f1a67972
    @PatchMapping("/finalizar/{id}")
    public ResponseEntity<DTOPrueba> finalizarPrueba(@PathVariable Long id, @RequestBody(required = false) DTOComentario comentario) {
        Prueba prueba = pruebaServicio.finalizar(id, comentario);
        return new ResponseEntity<>(new DTOPrueba(prueba), HttpStatus.OK);
    }


    @ExceptionHandler(PruebaException.class)
    public ResponseEntity<ErrorResponse> handlePruebaException(PruebaException ex) {
        ErrorResponse respuesta = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
     }
}

