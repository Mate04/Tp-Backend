package com.microservice.notificacion.repository;

import com.microservice.notificacion.entities.NotificacionAdvertencia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryNotificacionAdvetencia extends CrudRepository<NotificacionAdvertencia,Long> {

    List<NotificacionAdvertencia> findAllByLegajoEmpleado(Long legajo);
}
