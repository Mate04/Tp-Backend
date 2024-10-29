package com.microservice.logica.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordenada {
    private double longitud; // <- x
    private double latitud; // <- y

    public double calcularDistancia(Coordenada punto){
        double deltaX = (longitud - punto.getLongitud());
        double deltaY = (latitud - punto.getLatitud());
        //calculo la distancia aplicando pitagoras
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
<<<<<<< HEAD
    public boolean pasoDistanciaMaxima(Coordenada punto, float distanciaMax){
        float distancia = calcularDistancia(punto);
=======
    public boolean pasoDistanciaMaxima(Coordenada punto, double distanciaMax){
        double distancia = calcularDistancia(punto);
>>>>>>> 0d83a86b1c0eb41e95045e6dd46c6f6617cc9d55
        return distancia > distanciaMax;
    }
}
