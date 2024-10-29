package com.microservice.logica.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordenada {
    private double longitud; // <- x
    private double latitud; // <- y

    public double calcularDistancia(Coordenada punto) {
        double deltaX = (longitud - punto.getLongitud());
        double deltaY = (latitud - punto.getLatitud());
        //calculo la distancia aplicando pitagoras
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public boolean pasoDistanciaMaxima(Coordenada punto, double distanciaMax) {
        double distancia = calcularDistancia(punto);
        return distancia > distanciaMax;
    }
}
