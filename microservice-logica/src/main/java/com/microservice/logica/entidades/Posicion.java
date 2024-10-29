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
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    private double latitud;

    private double longitud;

    public Posicion(Vehiculo vehiculo, double latitud,double longitud){
        this.vehiculo = vehiculo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @PrePersist
    protected void onCreate() {
        fechaHora = LocalDateTime.now();
    }



}
