package com.microservice.logica.controladores;

import com.microservice.logica.entidades.Prueba;
import com.microservice.logica.servicios.InteresadoServicioImpl;
import com.microservice.logica.servicios.PruebaService;
import com.microservice.logica.servicios.VehiculoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void crearPrueba(@RequestBody Prueba prueba){
        prueba.getEmpleado();
        //pruebaServicio.save(prueba);
    }
}
