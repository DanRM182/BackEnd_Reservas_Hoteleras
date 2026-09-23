package com.reservasHotel.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaSalidaRequest(
        @Schema(
                description = "Fecha de salida de la reserva",
                example = "25/09/2026", type = "string", format = "date")
        @NotNull(message = "La fecha de salida es requerida")
        @FutureOrPresent(message = "la salida debe ser hoy o posterior")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaSalida
) {
}
