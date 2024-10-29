package com.microservice.logica.controladores.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;



import java.util.Date;

@Data
@AllArgsConstructor
public class DTOFechaPeriodo {
    private Date fechaInicio;
    private Date fechaFin;
}
