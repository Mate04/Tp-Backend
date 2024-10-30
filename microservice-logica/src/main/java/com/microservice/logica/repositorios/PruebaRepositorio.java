package com.microservice.logica.repositorios;

import com.microservice.logica.entidades.Prueba;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepositorio extends CrudRepository<Prueba, Long> {
    @Query("SELECT s FROM Prueba s where s.fechaFin is null ")
    List<Prueba> findAllByFechaFinNula();

    @Query("SELECT s FROM Prueba s where s.huboIncidente = true")
    List<Prueba> findAllByHuboIncidente();

    @Query("SELECT s FROM Prueba s WHERE s.vehiculo.id = :idVehiculo")
    List<Prueba> findAllByVehiculoId(Long idVehiculo);

}
