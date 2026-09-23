package com.reservasHotel.commons.dto.habitacion;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HabitacionReservaRequest(
        @Schema(description = "Identificador de la reserva que la solicita", example = "25")
        @NotNull(message = "El id de la reserva es requerido")
        @Positive(message = "El id de la reserva debe de ser positivo")
        Long idReserva
) {
}
