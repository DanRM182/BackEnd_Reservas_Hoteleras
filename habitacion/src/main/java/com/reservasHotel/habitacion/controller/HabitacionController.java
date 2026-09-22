package com.reservasHotel.habitacion.controller;

import com.reservasHotel.commons.controller.CrudController;
import com.reservasHotel.commons.dto.habitacion.HabitacionEstadoRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.habitacion.services.HabitacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habitaciones")
@Tag(name = "Api habitaciones", description = "Metodos para la gestion de habitacionesd")
public class HabitacionController extends CrudController <HabitacionRequest, HabitacionResponse, HabitacionService>{
    public HabitacionController(HabitacionService service){
        super(service);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<HabitacionResponse> actualizarEstado(
            @PathVariable("id")
            @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody HabitacionEstadoRequest request
    ) {
        return ResponseEntity.ok(
                service.actualizarEstado(id, request.estado())
        );
    }


}
