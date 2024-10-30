package com.microservice.logica.servicios;

import com.microservice.logica.client.ServicioAgencia;
import com.microservice.logica.client.ServicioNotificacion;
import com.microservice.logica.controladores.DTO.notificacion.DTONotiPromocion;
import com.microservice.logica.controladores.DTO.report.DTOVehiculoReport;
import com.microservice.logica.entidades.Interesado;
import com.microservice.logica.entidades.Posicion;
import com.microservice.logica.entidades.Vehiculo;
import com.microservice.logica.excepciones.PruebaException;
import com.microservice.logica.repositorios.ModeloRepositorio;
import com.microservice.logica.repositorios.VehiculoRepostorio;
import com.microservice.logica.utils.Coordenada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImp implements IServicio<Vehiculo,Long> {


    @Autowired
    private ServicioNotificacion servicioNotificacion;
    @Autowired
    private ServicioAgencia servicioAgencia;
    @Autowired
    private InteresadoService interesadoService;
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
    public void finalizarPruebaVehiculo(Vehiculo vehiculo) {
        //TODO: ejecutar logica de que mande la ultima posicion de la agencia
        vehiculo.setDisponible(true);
        Coordenada agencia = servicioAgencia.obtenerCoordenadaAgencia();
        Posicion posicion = new Posicion(vehiculo,agencia.getLatitud(),agencia.getLongitud());
        vehiculo.getPosiciones().add(posicion);
        this.update(vehiculo);
        //TODO: ejecutar logica de notificar a interesados
        //verificando que vehiculo tenga interesado
        if(!vehiculo.getInteresados().isEmpty()){
            System.out.println("Mandando notificacion");
            //creo una lista de DTO para poder mandarle a nuestro servicio
            List<DTONotiPromocion> dtoNotiPromocions = vehiculo.getInteresados().stream()
                    .map(interesado -> new DTONotiPromocion(
                            interesado.getTelefono(),
                            interesado.getNombre()+", "+
                            "Se liberó " +
                                    vehiculo.getModelo().getMarca().getNombre()
                                    + " " + vehiculo.getModelo().getNombre()
                    ))
                    .collect(Collectors.toList());
            servicioNotificacion.enviarNotificacionPromocion(dtoNotiPromocions);
        }
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

    public void addInteresadoVehiculo(Long id, Interesado interesadoBuscar) {
        Vehiculo vehiculo = this.findByID(id);
        Interesado interesado = interesadoService.findByID(interesadoBuscar.getDocumento());
        interesado.getVehiculosInteresados().add(vehiculo);
        interesadoService.update(interesado);
    }

}
