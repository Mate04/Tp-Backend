package com.microservice.logica.client;

import com.microservice.logica.controladores.DTO.DTONotificacionAdvertencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microservice.logica.controladores.DTO.notificacion.DTONotiPromocion;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.utils.AdaptadorFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioNotificacion {
    private Gson gson ;
    private String URL;
    @Autowired
    private RestTemplate restTemplate;


    public ServicioNotificacion(){
        URL = "http://localhost:9090/api/notificacion/";
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new AdaptadorFecha()).create();
    }


    public void enviarNotificacionAdvertencia(DTONotificacionAdvertencia notificacionAdvertencia) {
        try{
            String notificacionString = gson.toJson(notificacionAdvertencia);
            String url = URL + "advertencia/";
            // Crear los headers con el Content-Type adecuado
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);  // Content-Type: application/json

            HttpEntity<String> request = new HttpEntity<>(notificacionString, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        }catch (Exception e){
            System.err.println("Error al enviar notificacion advertencia");
        }
    }
    public void enviarNotificacionPromocion(List<DTONotiPromocion> notiPromociones){
        try {
            String notificacionString = gson.toJson(notiPromociones);
            String url = URL + "promocion/list";
            // Crear los headers con el Content-Type adecuado
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);  // Content-Type: application/json

            HttpEntity<String> request = new HttpEntity<>(notificacionString, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (!response.getStatusCode().is2xxSuccessful()){
                System.err.println("Error al enviar notificacion promocion"+ response.getStatusCode());
                return;
            }
            System.out.println("Se enviaron las notificaciones correctante");
        }catch (Exception e){
            System.err.println("Error al enviar notificacion promocion");
        }

    }



}
