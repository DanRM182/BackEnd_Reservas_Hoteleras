package com.reservasHotel.commons.dto.habitacion;

import com.reservasHotel.commons.enums.EstadoHabitacion;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Información de la habitacion devuelta por la API")
public record HabitacionResponse(
        @Schema(description = "Identificador de la habitacion", example = "1")
        Long id,
        @Schema(description = "Numero de la habitacion", example = "101A")
        String numeroHabitacion,
        @Schema(description = "Tipo de habitacion", example = "SUITE")
        TipoHabitacion tipo,
        @Schema(description = "Precio de la habitacion", example = "1500")
        BigDecimal precio,
        @Schema(description = "capacidad de las habitaciones", example = "5")
        Integer capacidad,
        @Schema(description = "Estado de la habitacion", example = "DISPONIBLE")
        EstadoHabitacion estado,
        @Schema(description = "estado del registro de la habitacion", example = "ACTIVO")
        EstadoRegistro estadoRegistro

) {
}
