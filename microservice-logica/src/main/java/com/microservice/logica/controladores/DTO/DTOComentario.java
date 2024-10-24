package com.microservice.logica.controladores.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DTOComentario {
    String comentario;

    public DTOComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setComentario(@JsonProperty("comentario") String comentario) {
        this.comentario = comentario;
    }
}
