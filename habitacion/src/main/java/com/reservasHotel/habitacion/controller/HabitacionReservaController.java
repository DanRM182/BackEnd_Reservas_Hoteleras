package com.reservasHotel.habitacion.controller;

import com.reservasHotel.commons.dto.habitacion.HabitacionReservaRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.habitacion.services.HabitacionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/habitaciones")
@RequiredArgsConstructor
@Validated
public class HabitacionReservaController {
    private final HabitacionService service;
    @PostMapping("/{idHabitacion}/ocupar")
    public ResponseEntity<HabitacionResponse> ocuparPorReserva(
            @PathVariable("idHabitacion")
            @Positive(message = "El id de la habitaciondebe ser positivo")
            Long idHabitacion,
            @Valid @RequestBody HabitacionReservaRequest request){
        return ResponseEntity.ok(
                service.ocuparPorReserva(idHabitacion,
                        request.idReserva()));
    }

    @PostMapping("/{idHabitacion}/liberar")
    public ResponseEntity<HabitacionResponse> liberarPorReserva(
            @PathVariable("idHabitacion")
            @Positive(message = "El id de la habitación debe ser positivo")
            Long idHabitacion,
            @Valid @RequestBody HabitacionReservaRequest request
    ) {
        return ResponseEntity.ok(
                service.liberarPorReserva(idHabitacion,
                        request.idReserva())
        );
    }
}
