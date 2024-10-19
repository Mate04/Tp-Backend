package com.microservice.logica.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
public class Empleado {
    @Id @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long legajo;

    private String nombre;

    private String apellido;

    @Column(name = "telefono_contacto", unique = true)
    private Long telefonoContacto;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Prueba> pruebas;
}
