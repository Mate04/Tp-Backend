package com.microservice.logica.controladores.DTO;

import com.google.gson.annotations.SerializedName;
import com.microservice.logica.utils.Coordenada;
import com.microservice.logica.utils.ZonaPeligrosa;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class DTOAgencia {
    @SerializedName("coordenadasAgencia")
    private  Coordenada coordenada;

    @SerializedName("radioAdmitidoKm")
    private double radioMaximo;

    @SerializedName("zonasRestringidas")
    List<ZonaPeligrosa> zonasPeligrosas;

    public void inicializarCoordenandas(){
        zonasPeligrosas.forEach(zonaPeligrosa -> zonaPeligrosa.inicializarCoordenadas());
    }
}
