package com.reservasHotel.habitacion.mapper;

import com.reservasHotel.commons.dto.habitacion.HabitacionRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.commons.enums.EstadoHabitacion;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.enums.TipoHabitacion;
import com.reservasHotel.commons.mapper.CommonMapper;
import com.reservasHotel.habitacion.entity.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion>  {
   @Override
    public Habitacion requestAEntidad(HabitacionRequest request) {
        return request != null ?
                Habitacion.crear(
                        request.numeroHabitacion().trim(),
                        TipoHabitacion.obtenerTipoHabitacionPorCodigo(
                                request.idTipoHabitacion()),
                        request.precio(),
                        request.capacidad()) : null;
    }




    public HabitacionResponse entidadAResponse(Habitacion entidad) {
        return entidad != null
                ? new HabitacionResponse(
                entidad.getId(),
                entidad.getNumeroHabitacion(),
                entidad.getTipoHabitacion().getDescripcion(),
                entidad.getEstadoHabitacion().getDescripcion(),
                entidad.getPrecio(),
                entidad.getCapacidad()
        )
                : null;
    }
}
