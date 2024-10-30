package com.microservice.logica.servicios;

import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.repositorios.InteresadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteresadoService implements IServicio<Interesado, Long>{

    @Autowired
    InteresadoRepositorio repositorio;


    @Override
    public List<Interesado> findAll() {
        return List.of();
    }

    @Override
    public Interesado findByID(Long id) {
        return repositorio.findById(id).orElseThrow(()->new PruebaException("No se encontro el interesado con el id: "+id));
    }

    @Override
    public void save(Interesado entity) {
        repositorio.save(entity);
    }
    public void update(Interesado entity) {
        repositorio.save(entity);
    }
}
