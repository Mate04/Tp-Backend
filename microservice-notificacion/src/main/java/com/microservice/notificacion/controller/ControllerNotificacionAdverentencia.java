package com.microservice.notificacion.controller;

import com.microservice.notificacion.entities.NotificacionAdvertencia;
import com.microservice.notificacion.service.ServiceNotificacionAdverentencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notificacion/advertencia")
public class ControllerNotificacionAdverentencia {

    @Autowired
    ServiceNotificacionAdverentencia serviceNotificacionAdverentencia;

    @PostMapping("/")
    public String createNotificacion(@RequestBody NotificacionAdvertencia notificacionAdvertencia) {
        serviceNotificacionAdverentencia.save(notificacionAdvertencia);
        return notificacionAdvertencia.toString();
    };

    @GetMapping("/empleado/{legajo}")
    public List<NotificacionAdvertencia> buscarIncidentesDeEmpleado(@PathVariable Long legajo) {
        return serviceNotificacionAdverentencia.buscarIncidentesPorEmpleado(legajo);
    }

}
