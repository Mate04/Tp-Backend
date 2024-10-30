package com.microservice.logica.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.logica.utils.Coordenada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonIgnore
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Posicion> posiciones;

    @JsonIgnore
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Prueba> pruebas;

    @JsonIgnore
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

    private BigDecimal calcularDistancia(List<Posicion> posicionesCalcular){
        double resultado = 0;
        //TODO: aca poner la coordenada de la agencia como primera
        Coordenada coordenadaAnterior = new Coordenada(0,0);
        for (Posicion posicion : posicionesCalcular) {
            Coordenada coordenadaActual = new Coordenada(posicion.getLongitud(),posicion.getLatitud());
            resultado += coordenadaAnterior.calcularDistancia(coordenadaActual);
            coordenadaAnterior = coordenadaActual;
        }
        return new BigDecimal(resultado);
    }
    public BigDecimal calcularDistanciaEnPeriodo(Date fechaInicial, Date fechaFinal){
        LocalDateTime inicio = fechaInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        //toma hasta el ultimo del dia
        LocalDateTime fin = fechaFinal.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                    .withHour(23).withMinute(59).withSecond(59).withNano(999999999);


        List<Posicion> posicionesRango = posiciones.stream()
                                        .filter(posicion -> !posicion.getFechaHora().isBefore(inicio) && !posicion.getFechaHora().isAfter(fin))
                                        .collect(Collectors.toList());
        return calcularDistancia(posicionesRango);
    }
}