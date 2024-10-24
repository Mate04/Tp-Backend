package com.microservice.logica;

import com.microservice.logica.utils.Coordenada;
import com.microservice.logica.utils.ZonaPeligrosa;

public class Main {
    public static void main(String[] args) {
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
    }
}
