package com.microservice.notificacion.service;

import java.util.List;

public interface IService<T,K> {
    List<T> findAll();
    T findByID(K id);
    void save(T entity);
}