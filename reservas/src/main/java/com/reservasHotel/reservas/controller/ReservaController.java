package com.reservasHotel.reservas.controller;

import com.reservasHotel.commons.controller.CrudController;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import com.reservasHotel.reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController@Validated
@Tag(name="API Reservas", description = "Métodos para la gestión de reservas")
public class ReservaController extends CrudController<ReservaRequest, ReservaResponse, ReservaService> {
    public ReservaController(ReservaService service) {
        super(service);
    }


    @Operation(
            summary = "Validar estado de reservas del huésped",
            description = "Valida si el huésped tiene reservas en estado EN_CURSO."
    )

    @GetMapping("/{idHuesped}/estadoReservasHuesped")
    public ResponseEntity<Void> validarEstadoReservasHuesped(
            @PathVariable
            @Positive(message = "El idHuesped debe ser positivo") Long idHuesped
    ) {

        service.validarEstadoReservasHuesped(idHuesped);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idReserva}/estado/{idEstado}")
    public ResponseEntity<ReservaResponse> actualizarEstado(
            @PathVariable("idReserva")
            @Positive(message = "El id de la reserva debe ser positivo")
            Long idReserva,
            @PathVariable("idEstado")
            @Positive(message = "El id del estado debe de ser positivo")
            Long idEstado
    ){
        return ResponseEntity.ok(service.actualizarEstado(idReserva,idEstado));
    }
}
