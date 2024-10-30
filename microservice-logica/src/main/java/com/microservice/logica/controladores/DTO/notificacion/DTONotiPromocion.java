package com.microservice.logica.controladores.DTO.notificacion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTONotiPromocion {
    private Long telefonoInteresado;
    private String mensaje;
}
