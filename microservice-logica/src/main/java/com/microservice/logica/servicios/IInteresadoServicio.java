package com.microservice.logica.servicios;

import com.microservice.logica.entidades.Interesado;

import java.util.List;

public interface IInteresadoServicio {

    List<Interesado> findAll();

    Interesado findById(Long id);

    void save(Interesado interesado);

}
