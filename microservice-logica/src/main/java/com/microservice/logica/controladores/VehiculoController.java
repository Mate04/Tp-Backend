package com.microservice.logica.controladores;

import com.microservice.logica.controladores.DTO.DTOFechaPeriodo;
import com.microservice.logica.controladores.DTO.DTOVehiculo;
import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.ErrorResponse;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.servicios.VehiculoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/recorrido/{id}")
    public ResponseEntity<?> findVehiculoById(
            @PathVariable("id") Long id,
            @RequestBody DTOFechaPeriodo fechaPeriodo) {
        if (fechaPeriodo.getFechaInicio() == null || fechaPeriodo.getFechaFin() == null) {
            throw new PruebaException("Las fechas de inicio y fin son obligatorias");
        }
        DTOVehiculo resultado = vehiculoServiceImp.getReporteDistanciaVehiculo(id,fechaPeriodo.getFechaInicio(),fechaPeriodo.getFechaFin());
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
    @PatchMapping("/{id}/interesado")
    public String addInteresado(@PathVariable("id") Long id, @RequestBody Interesado interesado) {
        vehiculoServiceImp.addInteresadoVehiculo(id, interesado);
        return "id: "+id + " | documento:"+interesado.getDocumento();

    }


    //manejo de error
    @ExceptionHandler(PruebaException.class)
    public ResponseEntity<ErrorResponse> handlePruebaException(PruebaException ex) {
        ErrorResponse respuesta = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
