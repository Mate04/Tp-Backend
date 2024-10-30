package com.microservice.notificacion.service;

import com.microservice.notificacion.entities.NotificacionPromocion;
import com.microservice.notificacion.repository.RepositoryNotificacionPromocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceNotificacionPromocion implements IService<NotificacionPromocion,Long>{

    @Autowired
    RepositoryNotificacionPromocion repositoryNotificacionPromocion;

    @Override
    public List<NotificacionPromocion> findAll() {
        return List.of();
    }

    @Override
    public NotificacionPromocion findByID(Long id) {
        return null;
    }

    @Override
    public void save(NotificacionPromocion entity) {
        repositoryNotificacionPromocion.save(entity);
    }
}
