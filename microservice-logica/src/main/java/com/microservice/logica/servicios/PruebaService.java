package com.microservice.logica.servicios;

import com.microservice.logica.entidades.Prueba;
import com.microservice.logica.repositorios.PruebaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PruebaService implements Servicio<Prueba, Long> {

    @Autowired
    private PruebaRepositorio pruebaRepositorio;

    @Override
    public List<Prueba> findAll() {
        return (List<Prueba>) pruebaRepositorio.findAll();
    }

    @Override
    public Prueba findByID(Long id) {
        return pruebaRepositorio.findById(id).orElseThrow();
    }

    @Override
    public void save(Prueba entity) {
        pruebaRepositorio.save(entity);
    }

    public List<Prueba> buscarPruebasEnCurso() {
        return (List<Prueba>) pruebaRepositorio.findAllByFechaFinNula();
    }
}