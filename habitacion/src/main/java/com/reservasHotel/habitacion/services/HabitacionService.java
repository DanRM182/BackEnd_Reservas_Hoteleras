package com.reservasHotel.habitacion.services;

import com.reservasHotel.commons.dto.habitacion.HabitacionRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.commons.enums.EstadoHabitacion;
import com.reservasHotel.commons.service.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
    HabitacionResponse actualizarEstado(Long id, EstadoHabitacion nuevoEstado);
    HabitacionResponse ocuparPorReserva(Long idHabitacion, Long idReserva);
    HabitacionResponse liberarPorReserva(Long idHabitacion, Long idReserva);

}
