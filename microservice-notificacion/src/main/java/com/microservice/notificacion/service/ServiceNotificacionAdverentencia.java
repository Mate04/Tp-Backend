package com.microservice.notificacion.service;

import com.microservice.notificacion.entities.NotificacionAdvertencia;
import com.microservice.notificacion.repository.RepositoryNotificacionAdvetencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceNotificacionAdverentencia implements IService<NotificacionAdvertencia,Long> {

    @Autowired
    RepositoryNotificacionAdvetencia repoNotificacionAdvetencia;


    @Override
    public List<NotificacionAdvertencia> findAll() {
        return List.of();
    }

    @Override
    public NotificacionAdvertencia findByID(Long id) {
        return null;
    }

    @Override
    public void save(NotificacionAdvertencia entity) {
        repoNotificacionAdvetencia.save(entity);
    }
    public List<NotificacionAdvertencia> buscarIncidentesPorEmpleado(Long legajo) {
        return repoNotificacionAdvetencia.findAllByLegajoEmpleado(legajo);
    }
}
