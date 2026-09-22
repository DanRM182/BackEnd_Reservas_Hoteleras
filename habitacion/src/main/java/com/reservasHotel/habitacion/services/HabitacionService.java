package com.reservasHotel.habitacion.services;

import com.dan.commons.dto.habitacion.HabitacionRequest;
import com.dan.commons.dto.habitacion.HabitacionResponse;
import com.dan.commons.enums.EstadoHabitacion;
import com.dan.commons.service.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
    HabitacionResponse actualizarEstado(Long id, EstadoHabitacion nuevoEstado);
}
