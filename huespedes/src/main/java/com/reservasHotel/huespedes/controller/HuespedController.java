package com.reservasHotel.huespedes.controller;

import com.reservasHotel.commons.controller.CrudController;
import com.reservasHotel.commons.dto.huespedes.HuespedRequest;
import com.reservasHotel.commons.dto.huespedes.HuespedResponse;
import com.reservasHotel.huespedes.services.HuespedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "API Huéspedes", description = "Métodos para gestión de huéspedes")
public class HuespedController extends CrudController<HuespedRequest, HuespedResponse, HuespedService> {
    public HuespedController(HuespedService service) {
        super(service);
    }

    @GetMapping("/id-paciente/{id}")
    @Operation(summary = "Obtener huésped por ID sin importar el estado del registro")
    public ResponseEntity<HuespedResponse> obtenerPacientePorIdSinEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(service.obtenerPorIdSinEstado(id));
    }
}
