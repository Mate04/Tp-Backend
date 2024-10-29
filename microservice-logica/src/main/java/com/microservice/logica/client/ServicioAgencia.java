package com.microservice.logica.client;

import com.microservice.logica.utils.Coordenada;
import com.microservice.logica.utils.ZonaPeligrosa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioAgencia {

    public List<ZonaPeligrosa> obtenerZonasPeligrosas() {
        //TODO: CREAMOS LISTA MOMENTANEA HASTA TENER EL SERVICIO DE AGENCIA

        Coordenada noroeste1 = new Coordenada(-10, 10);
        Coordenada sureste1 = new Coordenada(-5, 5);

        Coordenada noroeste2 = new Coordenada(-65.0, -32.0);
        Coordenada sureste2 = new Coordenada(-64.8, -32.2);

        // Crear las zonas peligrosas
        ZonaPeligrosa zona1 = new ZonaPeligrosa(noroeste1, sureste1);
        ZonaPeligrosa zona2 = new ZonaPeligrosa(noroeste2, sureste2);

        return new ArrayList<>(List.of(zona1, zona2));
    }

    public Coordenada obtenerCoordenadaAgencia() {
        return new Coordenada(0,0);
    }

    public Double obtenerRadioMaximo() {
        return 300D;
    }
}
