package com.microservice.logica.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordenada {
    private double longitud; // <- x
    private double latitud; // <- y

    public float calcularDistancia(Coordenada punto){
        float deltaX = (float) (longitud - punto.getLongitud());
        float deltaY = (float) (latitud - punto.getLatitud());
        //calculo la distancia aplicando pitagoras
        return  (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
