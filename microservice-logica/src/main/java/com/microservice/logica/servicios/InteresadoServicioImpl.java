package com.microservice.logica.servicios;

import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.repositorios.InteresadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteresadoServicioImpl implements IInteresadoServicio{

    @Autowired
    private InteresadoRepositorio interesadoRepositorio;

    @Override
    public List<Interesado> findAll() {
        return (List<Interesado>) interesadoRepositorio.findAll();
    }

    @Override
    public Interesado findById(Long id) {
        return interesadoRepositorio.findById(id).orElseThrow();
    }

    @Override
    public void save(Interesado interesado) {
        interesadoRepositorio.save(interesado);
    }
}