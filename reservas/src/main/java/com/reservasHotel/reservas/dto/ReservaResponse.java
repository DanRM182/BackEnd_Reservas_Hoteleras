package com.reservasHotel.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservasHotel.commons.dto.habitacion.DatosHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Información de una reserva")
public record ReservaResponse(
        @Schema(description = "Identificador único de la reserva", example = "1")
        Long id,

        @Schema(description = "Información de huésped asociado a la reserva")
        Object huesped,

        @Schema(description = "Información de la habitación asociada a la reserva")
        DatosHabitacion habitacion,

        @Schema(description = "Estado actual de la reserva", example = "Reservación creada")
        String estadoReserva,

        @Schema(description = "Fecha programada para la reserva", example = "25/09/2026",
                type = "string", format = "date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaEntrada,

        @Schema(description = "Fecha programada para la reserva", example = "25/09/2026",
                type = "string", format = "date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate fechaSalida
) { }