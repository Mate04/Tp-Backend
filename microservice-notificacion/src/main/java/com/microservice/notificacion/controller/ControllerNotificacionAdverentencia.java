package com.microservice.notificacion.controller;

import com.microservice.notificacion.entities.NotificacionAdvertencia;
import com.microservice.notificacion.service.ServiceNotificacionAdverentencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
