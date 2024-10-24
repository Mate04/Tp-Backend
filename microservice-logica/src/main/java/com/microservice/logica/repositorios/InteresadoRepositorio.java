package com.microservice.logica.repositorios;

import com.microservice.logica.entidades.Interesado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteresadoRepositorio extends CrudRepository<Interesado, Long> {

}
