package com.microservice.logica.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microservice.logica.controladores.DTO.DTONotificacionAdvertencia;
import com.microservice.logica.entidades.Posicion;
import com.microservice.logica.entidades.Prueba;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.repositorios.PosicionRepositorio;
import com.microservice.logica.utils.AdaptadorFecha;
import com.microservice.logica.utils.Coordenada;
import com.microservice.logica.utils.ZonaPeligrosa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PosicionService implements IServicio<Posicion,Long> {

    @Autowired
    PosicionRepositorio posicionRepositorio;
    @Autowired
    VehiculoServiceImp vehiculoServiceImp;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Posicion> findAll() {
        return (List<Posicion>) posicionRepositorio.findAll();
    }

    @Override
    public Posicion findByID(Long id) {
        return posicionRepositorio.findById(id).orElseThrow(() -> new PruebaException("La posicion no fue encontrada"));
    }

    @Override
    public void save(Posicion posicionAValidar) {
        String advertencia;
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new AdaptadorFecha()).create();

        Vehiculo vehiculo = vehiculoServiceImp.findByID(posicionAValidar.getVehiculo().getId());
        if (vehiculo.isDisponible()) {
            throw new PruebaException("El vehiculo no se encuentra en una prueba");
        }
        Coordenada posicionActual = new Coordenada(posicionAValidar.getLongitud(), posicionAValidar.getLatitud());

        //TODO: CREAMOS LISTA MOMENTANEA HASTA TENER EL SERVICIO DE AGENCIA
        Coordenada coordenaAgencia = new Coordenada(0,0);
        Double radioMaximoAgencia = 300D;

        Coordenada noroeste1 = new Coordenada(-64.0, -31.4);
        Coordenada sureste1 = new Coordenada(-63.9, -31.5);

        Coordenada noroeste2 = new Coordenada(-65.0, -32.0);
        Coordenada sureste2 = new Coordenada(-64.8, -32.2);

        // Crear las zonas peligrosas
        ZonaPeligrosa zona1 = new ZonaPeligrosa(noroeste1, sureste1);
        ZonaPeligrosa zona2 = new ZonaPeligrosa(noroeste2, sureste2);

        List<ZonaPeligrosa> zonasPeligrosas = new ArrayList<>(List.of(zona1, zona2));
        boolean estaEnZonaPeligrosa = zonasPeligrosas.stream().anyMatch(zonaPeligrosa -> zonaPeligrosa.contiene(posicionActual));
        boolean pasoDistanciaMaxima = posicionActual.pasoDistanciaMaxima(coordenaAgencia,radioMaximoAgencia);
        //aca lanzamos la advertencia

        if (pasoDistanciaMaxima || estaEnZonaPeligrosa) {
            Prueba prueba = vehiculo.obtenerPruebaActual();
            DTONotificacionAdvertencia notificacion = new DTONotificacionAdvertencia("algo malo hizo", prueba.getEmpleado().getLegajo(),prueba.getEmpleado().getTelefonoContacto());
            String notificacionString = gson.toJson(notificacion);
            String url = "http://localhost:9090/api/notificacion/advertencia/";

            // Crear los headers con el Content-Type adecuado
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);  // Content-Type: application/json

            // Crear la entidad con el cuerpo y los headers
            HttpEntity<String> request = new HttpEntity<>(notificacionString, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println(response.getBody());
        }


        posicionRepositorio.save(posicionAValidar);

    }


}
