package com.reservasHotel.commons.clients;

import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "habitacion")
public interface HabitacionClient {
    @GetMapping("/{id}")
    HabitacionResponse obtenerHabitacionActivaPorId(@PathVariable("id")Long id);

    @PatchMapping("/{id}/ocupar")
    HabitacionResponse ocuparPorReserva(@PathVariable("id")Long id);

    @PatchMapping("/{id}/liberar")
    HabitacionResponse liberarPorReserva(@PathVariable("id")Long id);

}
