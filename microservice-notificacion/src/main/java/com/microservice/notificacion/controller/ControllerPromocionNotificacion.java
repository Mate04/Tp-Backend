package com.microservice.notificacion.controller;


import com.microservice.notificacion.entities.NotificacionPromocion;
import com.microservice.notificacion.service.ServiceNotificacionPromocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/notificacion/promocion")
public class ControllerPromocionNotificacion {

    @Autowired
    private ServiceNotificacionPromocion serviceNotificacionPromocion;

    //Solo el empleado
    @PostMapping("/")
    public ResponseEntity<NotificacionPromocion> createMensage(@RequestBody NotificacionPromocion notificacionPromocion) {
        serviceNotificacionPromocion.save(notificacionPromocion);
        return ResponseEntity.ok(notificacionPromocion);
    }

    //Solo el empleado
    @PostMapping("/list")
    public ResponseEntity<List<NotificacionPromocion>> createListMensage(@RequestBody List<NotificacionPromocion> notificacionesPromociones) {
        List<NotificacionPromocion> savedNotifications = notificacionesPromociones.stream()
                .peek(serviceNotificacionPromocion::save) // Guarda cada notificación en el servicio
                .collect(Collectors.toList()); // Recoge la lista de notificaciones procesadas
        return ResponseEntity.ok(savedNotifications);
    }


}
