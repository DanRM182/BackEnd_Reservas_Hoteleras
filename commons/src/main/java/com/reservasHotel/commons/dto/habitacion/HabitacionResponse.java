package com.reservasHotel.commons.dto.habitacion;

import com.reservasHotel.commons.enums.EstadoHabitacion;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Información de una habitación")
public record HabitacionResponse(
        @Schema(description = "Identificador de la habitacion", example = "1")
        Long id,

        @Schema(description = "Numero de la habitacion", example = "101A")
        String numeroHabitacion,

        @Schema(description = "Tipo de habitacion", example = "SUITE")
        String tipoHabitacion,

        @Schema(description = "Estado de la habitacion", example = "DISPONIBLE")
        String estadoHabitacion,

        @Schema(description = "Precio de la habitacion", example = "1500")
        BigDecimal precio,

        @Schema(description = "capacidad de las habitaciones", example = "5")
        Integer capacidad
) {
}
