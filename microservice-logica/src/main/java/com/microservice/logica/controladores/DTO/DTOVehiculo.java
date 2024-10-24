package com.microservice.logica.controladores.DTO;

import com.microservice.logica.entidades.Modelo;
import com.microservice.logica.entidades.Vehiculo;
import lombok.Data;

@Data
public class DTOVehiculo {
    private Long id;
    private String patente;
    private String modelo;
    private String marca;
    private boolean disponible;

    public DTOVehiculo(Vehiculo vehiculo) {
        id = vehiculo.getId();
        patente = vehiculo.getPatente();
        modelo = vehiculo.getModelo().getNombre();
        marca = vehiculo.getModelo().getMarca().getNombre();
        disponible = vehiculo.isDisponible();
    }
}
