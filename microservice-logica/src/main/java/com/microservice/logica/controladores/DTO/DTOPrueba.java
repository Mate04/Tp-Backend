package com.microservice.logica.controladores.DTO;

import com.microservice.logica.entidades.Prueba;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DTOPrueba {
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long interesadoDocumento;
    private String vehiculoPatente;
    private String comentario;

    public DTOPrueba(Prueba prueba) {
        this.id = prueba.getId();
        this.fechaInicio = prueba.getFechaInicio();
        this.fechaFin = prueba.getFechaFin();
        this.interesadoDocumento = prueba.getInteresado().getDocumento();
        this.vehiculoPatente = prueba.getVehiculo().getPatente();
        this.comentario = prueba.getComentario();
    }
}
