package com.microservice.logica.servicios;

import com.microservice.logica.client.ServicioAgencia;
import com.microservice.logica.controladores.DTO.DTOComentario;
import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.entidades.Posicion;
import com.microservice.logica.entidades.Prueba;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.repositorios.PruebaRepositorio;
import com.microservice.logica.utils.Coordenada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PruebaService implements IServicio<Prueba, Long> {

    @Autowired
    private ServicioAgencia servicioAgencia;
    @Autowired
    private PruebaRepositorio pruebaRepositorio;
    @Autowired
    private InteresadoServicioImpl interesadoServicio;
    @Autowired
    private VehiculoServiceImp vehiculoServico;

    @Override
    public List<Prueba> findAll() {
        return (List<Prueba>) pruebaRepositorio.findAll();
    }

    @Override
    public Prueba findByID(Long id) {
        return pruebaRepositorio.findById(id).orElseThrow(() -> new PruebaException("no se encontro la prueba"));
    }

    @Override
    public void save(Prueba entity) {

        Interesado interesadoTraido = interesadoServicio.findByID(entity.getInteresado().getDocumento());

        if (interesadoTraido.isRestringido()) {
            throw new PruebaException("El interesado esta restringido");
        }
        if (!interesadoTraido.validarLicencia()){
            throw new PruebaException("El interesado no tiene licencia valida");
        }
        Vehiculo vehiculo = vehiculoServico.findByID(entity.getVehiculo().getId());
        if (!vehiculo.isDisponible()) {
            throw new PruebaException("El vehiculo no esta disponible");
        }
        if (interesadoTraido.tienePruebaEnCurso()) {
            throw new PruebaException("El interesado ya esta haciendo una prueba en curso");
        }

        vehiculo.setDisponible(false);
        vehiculoServico.update(vehiculo);
        pruebaRepositorio.save(entity);
    }
    public Prueba finalizar(Long id, DTOComentario comentario) {
        Prueba prueba = this.findByID(id);
        Vehiculo vehiculo = vehiculoServico.findByID(prueba.getVehiculo().getId());

        if (prueba.getFechaFin() != null) {
            throw new PruebaException("La prueba ya esta finalizada");
        }
        prueba.setFechaFin(LocalDateTime.now());

        if (comentario != null) {
            prueba.setComentario(comentario.getComentario());
        }

        vehiculo.setDisponible(true);
        //TODO: ejecutar logica de que mande la ultima posicion de la agencia
        Coordenada agencia = servicioAgencia.obtenerCoordenadaAgencia();
        Posicion posicion = new Posicion(vehiculo,agencia.getLatitud(),agencia.getLongitud());
        vehiculo.getPosiciones().add(posicion);
        vehiculoServico.update(vehiculo);
        return pruebaRepositorio.save(prueba);
    }

    public Prueba update(Prueba entity) {
        return pruebaRepositorio.save(entity);
    }

    public List<Prueba> buscarPruebasEnCurso() {
        return (List<Prueba>) pruebaRepositorio.findAllByFechaFinNula();
    }

    public List<Prueba> buscarPruebasConIncidentes(){
        return (List<Prueba>)  pruebaRepositorio.findAllByHuboIncidente();
    }

    public List<Prueba> buscarPruebasXVehiculo(Long idVehiculo) {
        return pruebaRepositorio.findAllByVehiculoId(idVehiculo);
    }
}

