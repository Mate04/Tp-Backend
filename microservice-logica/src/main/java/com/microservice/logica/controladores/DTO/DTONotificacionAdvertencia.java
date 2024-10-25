package com.microservice.logica.controladores.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DTONotificacionAdvertencia {
    String descripcionAdvertencia;
    LocalDateTime fechaHora = LocalDateTime.now();
    Long legajoEmpleado;
    Long telefonoEmpleado;
    public DTONotificacionAdvertencia(String descripcionAdvertencia, Long legajo, Long telefonoEmpleado) {
        this.descripcionAdvertencia = descripcionAdvertencia;
        this.legajoEmpleado = legajo;
        this.telefonoEmpleado= telefonoEmpleado;
    }

}
