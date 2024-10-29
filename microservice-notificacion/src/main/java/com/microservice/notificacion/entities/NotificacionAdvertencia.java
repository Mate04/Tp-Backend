package com.microservice.notificacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class NotificacionAdvertencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descripcion_advertencia")
    private String descripcionAdvertencia;

    @Column(name="legajo_empleado")
    private Long legajoEmpleado;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "telefono_empleado")
    private Long telefonoEmpleado;
}
