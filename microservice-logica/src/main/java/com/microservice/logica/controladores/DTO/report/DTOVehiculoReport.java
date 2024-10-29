package com.microservice.logica.controladores.DTO.report;

import com.microservice.logica.controladores.DTO.DTOVehiculo;
import com.microservice.logica.entidades.Vehiculo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)  // Para manejar equals/hashCode correctamente con herencia
public class DTOVehiculoReport extends DTOVehiculo {
    private String distanciaRecorrida; // Campo adicional para el reporte

    // Constructor que utiliza el constructor de la superclase
    public DTOVehiculoReport(Vehiculo vehiculo, BigDecimal distanciaRecorrida) {
        super(vehiculo);  // Llama al constructor de DTOVehiculo
        this.distanciaRecorrida = distanciaRecorrida.toPlainString();
    }
}