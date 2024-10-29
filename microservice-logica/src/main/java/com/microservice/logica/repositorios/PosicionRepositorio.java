package com.microservice.logica.repositorios;

import com.microservice.logica.entidades.Posicion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicionRepositorio extends CrudRepository<Posicion, Long> {
}
