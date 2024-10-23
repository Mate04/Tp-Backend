package com.microservice.logica.repositorios;

import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.entidades.Prueba;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteresadoRepositorio extends CrudRepository<Interesado, Long> {
    @Query("SELECT s FROM Prueba s where s.fechaFin is null and s.interesado.documento = :documento")
    List<Prueba> buscarPruebasEnCursoParaInteresado(@Param("documento") Long documento);
}
