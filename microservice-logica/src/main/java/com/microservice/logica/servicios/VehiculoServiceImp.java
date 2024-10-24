package com.microservice.logica.servicios;

import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.repositorios.VehiculoRepostorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImp implements Servicio<Vehiculo,Long>{


    @Autowired
    private VehiculoRepostorio vehiculoRepostorio;


    @Override
    public List<Vehiculo> findAll() {
        return (List<Vehiculo>) vehiculoRepostorio.findAll();
    }
    @Override
    public Vehiculo findByID(Long Id) {
        return vehiculoRepostorio.findById(Id).orElseThrow(() -> new PruebaException("No encontrado interesado"));
    }

    @Override
    public void save(Vehiculo entity) {
        vehiculoRepostorio.save(entity);
    }
    public void update(Vehiculo entity) {
        vehiculoRepostorio.save(entity);
    }
}
