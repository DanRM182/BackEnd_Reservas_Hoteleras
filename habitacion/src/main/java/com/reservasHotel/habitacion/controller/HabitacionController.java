package com.reservasHotel.habitacion.controller;

import com.reservasHotel.commons.controller.CrudController;
import com.reservasHotel.commons.dto.habitacion.HabitacionRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.habitacion.services.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Api habitaciones", description = "Metodos para la gestion de habitacionesd")
public class HabitacionController extends CrudController <HabitacionRequest, HabitacionResponse, HabitacionService>{
    public HabitacionController(HabitacionService service){
        super(service);
    }

    @GetMapping("/id-habitacion/{id}")
    @Operation(summary = "Obtener habitación por ID sin importar el estado del registro")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(service.obtenerPorIdSinEstado(id));
    }

    @PatchMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<HabitacionResponse> actualizarEstado(
            @PathVariable @Positive(message = "El idHabitacion debe ser positivo") Long id,
            @PathVariable @Positive(message = "El idEstado debe ser positivo") Long idEstado
    ) {
        return ResponseEntity.ok(
                service.actualizarEstado(id, idEstado)
        );
    }
}
