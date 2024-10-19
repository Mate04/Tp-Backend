package com.microservice.logica.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
public class Interesado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documento;

    private String nombre;
    private String apellido;

    private boolean restringido;

    @Column(name = "nro_licencia", unique = true, nullable = false)
    private Integer nroLicencia;

    @Column(unique = true)
    private Long telefono;

    @Column(name = "fecha_vencimiento_licencia", nullable = false)
    private Date fechaVencimientoLicencia;

    @OneToMany(mappedBy = "interesado", cascade = CascadeType.ALL)
    private List<Prueba> pruebas;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "interesados_por_vehiculo",
            joinColumns = @JoinColumn(name = "documento_interesado"),
            inverseJoinColumns = @JoinColumn(name = "id_vehiculo")
    )
    private List<Vehiculo> vehiculosInteresados;
}