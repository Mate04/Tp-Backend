package com.microservice.logica.client;

import com.google.gson.Gson;
import com.microservice.logica.controladores.DTO.DTOAgencia;
import com.microservice.logica.utils.Coordenada;
import com.microservice.logica.utils.ZonaPeligrosa;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioAgencia {

    private static ServicioAgencia instancia;

    private DTOAgencia dtoAgencia;

    private ServicioAgencia(){
        //llanada a api
        try {
            // Crea un cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Define la URL de la API y configura la solicitud
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/"))  // Cambia esta URL por la de tu API
                    .GET()
                    .build();

            // Envía la solicitud y recibe la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica el código de estado y procesa la respuesta
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                this.dtoAgencia = gson.fromJson(response.body(),DTOAgencia.class);
                dtoAgencia.inicializarCoordenandas();
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static ServicioAgencia getInstance(){
        if (instancia == null){
            instancia = new ServicioAgencia();
        }
        return instancia;
    }

    public List<ZonaPeligrosa> obtenerZonasPeligrosas() {
        return dtoAgencia.getZonasPeligrosas();
    }

    public Coordenada obtenerCoordenadaAgencia() {
        return dtoAgencia.getCoordenada();
    }

    public Double obtenerRadioMaximo() {
        return dtoAgencia.getRadioMaximo();
    }
}
