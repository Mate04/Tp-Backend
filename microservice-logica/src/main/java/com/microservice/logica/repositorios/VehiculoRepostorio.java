package com.microservice.logica.repositorios;

import com.microservice.logica.entidades.Vehiculo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepostorio extends CrudRepository<Vehiculo, Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vehiculo v WHERE v.patente = :patente")
    Boolean existsByPatente(@Param("patente") String patente);
}
