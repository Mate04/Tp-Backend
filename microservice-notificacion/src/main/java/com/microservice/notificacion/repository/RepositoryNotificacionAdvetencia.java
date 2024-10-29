package com.microservice.notificacion.repository;

import com.microservice.notificacion.entities.NotificacionAdvertencia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryNotificacionAdvetencia extends CrudRepository<NotificacionAdvertencia,Long> {

}
