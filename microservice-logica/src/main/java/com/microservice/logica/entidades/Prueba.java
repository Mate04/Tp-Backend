package com.microservice.logica.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "prueba")
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legajo_empleado")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_interesado")
    private Interesado interesado;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private LocalDateTime fechaFin;

    @Column(name = "hubo_incidente")
    private Boolean huboIncidente;


    private String comentario;

    // Método que se ejecuta antes de guardar el objeto en la base de datos
    @PrePersist
    protected void onCreate() {
        if (fechaInicio == null) {
            fechaInicio = LocalDateTime.now(); // Fecha y hora actual
        }
        this.setHuboIncidente(false);
    }


}