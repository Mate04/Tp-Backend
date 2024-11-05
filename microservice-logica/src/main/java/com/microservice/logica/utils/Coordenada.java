package com.microservice.logica.utils;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordenada {
    @SerializedName("lon")
    private double longitud; // <- x
    @SerializedName("lat")
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
