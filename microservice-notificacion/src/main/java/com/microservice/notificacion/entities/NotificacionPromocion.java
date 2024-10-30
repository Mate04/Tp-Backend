package com.microservice.notificacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class NotificacionPromocion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long telefonoInteresado;

    private String mensaje;
}
