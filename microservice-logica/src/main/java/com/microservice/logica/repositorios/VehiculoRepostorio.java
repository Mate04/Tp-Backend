package com.microservice.logica.repositorios;

import com.microservice.logica.entidades.Vehiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepostorio extends CrudRepository<Vehiculo, Long> {

}
