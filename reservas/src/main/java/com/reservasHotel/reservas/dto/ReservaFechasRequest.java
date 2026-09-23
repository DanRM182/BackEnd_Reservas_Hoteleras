package com.reservasHotel.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record ReservaFechasRequest(
        @Schema(
                description = "fecha de entrada/salida programada para entrada de la reserva. Debe ser una fecha y hora actuales",
                example = "25/09/2026", type = "string", format = "date")
        @NotNull(message = "La fecha correspondiente es requerida")
        @FutureOrPresent(message = "debe ser hoy o posterior")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaEntrada,
        @Schema(
                description = "fecha de entrada/salida programada para salida de la habitacion. Debe ser una fecha y hora actual ",
                example = "25/09/2026", type = "string", format = "date")
        @NotNull(message = "La fecha correspondiente es requerida")
        @FutureOrPresent(message = "debe ser hoy o posterior")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaSalida
) {
}
