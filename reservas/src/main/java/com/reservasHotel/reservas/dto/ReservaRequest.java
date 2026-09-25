package com.reservasHotel.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ReservaRequest(
        @Schema(description = "Identificador de la habitación", example = "5", minimum = "1")
        @NotNull(message = "El id de la habitación es requerido")
        @Positive(message = "El id de la habitación debe ser positivo")
        Long idHabitacion,

        @Schema(description = "Identificador del huésped", example = "1", minimum = "1")
        @NotNull(message = "El id del huésped es requerido")
        @Positive(message = "El id del huésped debe ser positivo")
        Long idHuesped,

        @Schema(
                description = "Fecha y hora programada para entrada de la reserva. Debe ser una fecha y hora actual o futura",
                example = "25/09/2026", type = "string", format = "date")
        @NotNull(message = "La fecha de entrada debe ser futura")
        //@FutureOrPresent(message = "La fecha de la entrada debe ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaEntrada,

        @Schema(
                description = "Fecha y hora programada para salida de la reserva. Debe ser una fecha y hora actual o futura",
                example = "28/09/2026", type = "string", format = "date")
        @NotNull(message = "La fecha de salida debe ser futura")
        @FutureOrPresent(message = "La fecha de la salida debe ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaSalida
) { }