package com.microservice.logica.servicios;

import java.util.List;

public interface Servicio <T,K>{
    List<T> findAll();
    T findByID(K id);
    void save(T entity);
}


























