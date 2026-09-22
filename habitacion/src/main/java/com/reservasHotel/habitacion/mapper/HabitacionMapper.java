package com.reservasHotel.habitacion.mapper;

import com.dan.commons.dto.habitacion.HabitacionRequest;
import com.dan.commons.dto.habitacion.HabitacionResponse;
import com.dan.commons.enums.EstadoHabitacion;
import com.dan.commons.enums.EstadoRegistro;
import com.dan.commons.mapper.CommonMapper;
import com.reservasHotel.habitacion.entity.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {
    @Override
    public Habitacion requestAEntidad(HabitacionRequest request) {
        if (request==null)return null;
        return Habitacion.builder()
                .numeroHabitacion(request.numeroHabitacion().trim())
                .tipo(request.tipo())
                .precio(request.precio())
                .capacidad(request.capacidad())
                .estado(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    @Override
    public HabitacionResponse entidadAResponse(Habitacion entidad) {
       if (entidad==null)return null;
       return new HabitacionResponse(
               entidad.getId(),
               entidad.getNumeroHabitacion(),
               entidad.getTipo(),
               entidad.getPrecio(),
               entidad.getCapacidad(),
               entidad.getEstado(),
               entidad.getEstadoRegistro());
    }
}
