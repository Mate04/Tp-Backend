package com.microservice.logica;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.GsonBuilder;
import com.microservice.logica.controladores.DTO.DTONotificacionAdvertencia;
import com.google.gson.Gson;
import com.microservice.logica.utils.AdaptadorFecha;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        /*
        Coordenada noreoeste = new Coordenada(-34,50);
        Coordenada sureste = new Coordenada(-28,40);
        ZonaPeligrosa zonaPeligrosa = new ZonaPeligrosa(noreoeste, sureste);
        Coordenada coordenada = new Coordenada(-30,50);
        System.out.println(zonaPeligrosa.contiene(coordenada));
         */
        Coordenada a = new Coordenada(3,1);
        Coordenada b = new Coordenada(5,2);
        System.out.println(a.calcularDistancia(b));
        System.out.println(a.pasoDistanciaMaxima(b,2.235F));
=======


>>>>>>> 0d83a86b1c0eb41e95045e6dd46c6f6617cc9d55
    }
}
