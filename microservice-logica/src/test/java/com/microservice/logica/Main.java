package com.microservice.logica;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.GsonBuilder;
import com.microservice.logica.controladores.DTO.DTONotificacionAdvertencia;
import com.google.gson.Gson;
import com.microservice.logica.utils.AdaptadorFecha;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        /*
        Coordenada noreoeste = new Coordenada(-34,50);
        Coordenada sureste = new Coordenada(-28,40);
        ZonaPeligrosa zonaPeligrosa = new ZonaPeligrosa(noreoeste, sureste);
        Coordenada coordenada = new Coordenada(-30,50);
        System.out.println(zonaPeligrosa.contiene(coordenada));
         */
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new AdaptadorFecha()).create();

        DTONotificacionAdvertencia notificacion = new DTONotificacionAdvertencia("nombre", 10L);

        String nombre;
        nombre = gson.toJson(notificacion);
        System.out.println(nombre);

    }
}
