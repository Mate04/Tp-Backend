package com.microservice.logica.utils;

import lombok.Data;

@Data
public class ZonaPeligrosa {
    private Coordenada coordenadaNoroeste;
    private Coordenada coordenadaSureste;
    private Coordenada coordenadaNoreste;
    private Coordenada coordenadaSuroeste;

    public ZonaPeligrosa(Coordenada coordenadaNoroeste , Coordenada coordenadaSureste) {
        this.coordenadaNoroeste = coordenadaNoroeste;
        this.coordenadaSureste = coordenadaSureste;
        this.coordenadaNoreste = new Coordenada(coordenadaSureste.getLongitud(),coordenadaNoroeste.getLatitud());
        this.coordenadaSuroeste = new Coordenada(coordenadaNoroeste.getLongitud(),coordenadaSureste.getLatitud());
    }



    public boolean contiene(Coordenada punto){
        double Ymax = coordenadaNoroeste.getLatitud();
        double Ymin = coordenadaSuroeste.getLatitud();
        double Xmin = coordenadaNoroeste.getLongitud();
        double Xmax = coordenadaSureste.getLongitud();
        if ((Ymin <= punto.getLatitud() && Ymax >= punto.getLatitud())
        && (Xmin <= punto.getLongitud() && Xmax >= punto.getLongitud()))
        {
            return true;
        }
        return false;
    }
}