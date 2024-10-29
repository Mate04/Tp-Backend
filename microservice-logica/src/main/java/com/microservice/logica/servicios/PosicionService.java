package com.microservice.logica.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microservice.logica.client.ServicioAgencia;
import com.microservice.logica.client.ServicioNotificacion;
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

import static com.microservice.logica.client.ServicioAgencia.*;

@Service
public class PosicionService implements IServicio<Posicion,Long> {

    @Autowired
    PosicionRepositorio posicionRepositorio;
    @Autowired
    VehiculoServiceImp vehiculoServiceImp;
    @Autowired
    ServicioAgencia servicioAgencia;
    @Autowired
    ServicioNotificacion servicioNotificacion;
    @Autowired
    private PruebaService pruebaService;

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

        Vehiculo vehiculo = vehiculoServiceImp.findByID(posicionAValidar.getVehiculo().getId());
        if (vehiculo.isDisponible()) {
            throw new PruebaException("El vehiculo no se encuentra en una prueba");
        }
        Coordenada posicionActual = new Coordenada(posicionAValidar.getLongitud(), posicionAValidar.getLatitud());
        Coordenada coordenaAgencia = servicioAgencia.obtenerCoordenadaAgencia();
        Double radioMaximoAgencia = servicioAgencia.obtenerRadioMaximo();
        List<ZonaPeligrosa> zonasPeligrosas = servicioAgencia.obtenerZonasPeligrosas();

        ZonaPeligrosa zonaPeligrosaDelVehiculo = zonasPeligrosas.stream()
                .filter(zonaPeligrosa -> zonaPeligrosa.contiene(posicionActual))
                .findFirst()
                .orElse(null);
        boolean pasoDistanciaMaxima = posicionActual.pasoDistanciaMaxima(coordenaAgencia,radioMaximoAgencia);
        //aca lanzamos la advertencia

        String mensajeAdvertencia = "";
        if (pasoDistanciaMaxima) {
            mensajeAdvertencia += "Paso la distancia maxima: " + radioMaximoAgencia + "metros, el vehiculo se encuentra a " + posicionActual.calcularDistancia(coordenaAgencia) + " de la agencia";
        }
        else if (zonaPeligrosaDelVehiculo != null) {
            mensajeAdvertencia += "El vehiculo se encuentra en zona peligrosa en las coordenadas";
        }
        if (pasoDistanciaMaxima || zonaPeligrosaDelVehiculo != null) {
            Prueba prueba = vehiculo.obtenerPruebaActual();
            DTONotificacionAdvertencia advertencia1;
            advertencia1 = new DTONotificacionAdvertencia(mensajeAdvertencia, prueba.getEmpleado().getLegajo(),prueba.getEmpleado().getTelefonoContacto());
            servicioNotificacion.enviarNotificacionAdvertencia(advertencia1);
            Prueba pruebaActual = vehiculo.obtenerPruebaActual();
            pruebaActual.setHuboIncidente(true);
            pruebaService.update(pruebaActual);
        }

        posicionRepositorio.save(posicionAValidar);

    }


}
