package com.microservice.logica.excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        throw new PruebaException("El cuerpo de la solicitud (RequestBody) es obligatorio");
    }
}
