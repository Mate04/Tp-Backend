package com.microservice.notificacion.repository;

import com.microservice.notificacion.entities.NotificacionPromocion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryNotificacionPromocion extends CrudRepository<NotificacionPromocion,Long> {
}
