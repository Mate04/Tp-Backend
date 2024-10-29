package com.microservice.logica.servicios;

import com.microservice.logica.controladores.DTO.report.DTOVehiculoReport;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.repositorios.ModeloRepositorio;
import com.microservice.logica.repositorios.VehiculoRepostorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class VehiculoServiceImp implements IServicio<Vehiculo,Long> {


    @Autowired
    private VehiculoRepostorio vehiculoRepostorio;
    @Autowired
    private ModeloRepositorio modeloRepositorio;

    @Override
    public List<Vehiculo> findAll() {
        return (List<Vehiculo>) vehiculoRepostorio.findAll();
    }
    @Override
    public Vehiculo findByID(Long Id) {
        return vehiculoRepostorio.findById(Id).orElseThrow(() -> new PruebaException("No encontrado vehiculo"));
    }

    @Override
    public void save(Vehiculo entity) {
        Boolean resultado = vehiculoRepostorio.existsByPatente(entity.getPatente());
        if (resultado) {
            throw new PruebaException("El vehiculo ya existe");
        }
        System.out.println(entity);
        modeloRepositorio.findById(entity.getModelo().getId())
                .orElseThrow(() -> new PruebaException("No encontrado modelo de vehiculo"));

        vehiculoRepostorio.save(entity);
    }

    public void update(Vehiculo entity) {
        vehiculoRepostorio.save(entity);
    }


    public DTOVehiculoReport getReporteDistanciaVehiculo(Long Id, Date fechaInicio, Date fechaFin) {
        Vehiculo vehiculo = this.findByID(Id);

        // Verificar si fechaInicio es mayor que fechaFin
        if (fechaInicio.after(fechaFin)) {
            // Intercambiar las fechas
            Date temp = fechaInicio;
            fechaInicio = fechaFin;
            fechaFin = temp;
        }
        return new DTOVehiculoReport(vehiculo,vehiculo.calcularDistanciaEnPeriodo(fechaInicio, fechaFin));


    }

}
