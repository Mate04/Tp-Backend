package com.microservice.logica.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String patente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    private boolean disponible;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Posicion> posiciones;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Prueba> pruebas;

    @ManyToMany(mappedBy = "vehiculosInteresados", cascade = CascadeType.ALL)
    private List<Interesado> interesados;

    //cuando se crea siempre se va a poner que esta disponible
    @PrePersist
    public void prePersist() {
            this.disponible = true;
    }

    public Prueba obtenerPruebaActual(){
        return pruebas.stream().filter( prueba -> prueba.getFechaFin() == null).findFirst().orElse(null);
    }
}