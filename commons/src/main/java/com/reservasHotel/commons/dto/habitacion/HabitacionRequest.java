package com.reservasHotel.commons.dto.habitacion;

import com.reservasHotel.commons.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para registrar o actualizar una habitacion")
public record HabitacionRequest(
        @Schema(description = "Número de la habitación", example = "101A")
        @NotBlank(message = "El número de habitación es requerido")
        @Size(min = 1, max = 20, message = "El número de la habitación debe ser de 1 a 20 caracteres")
        @Pattern(regexp = "^[1-9][0-9]*[A-Z]?$")
        String numeroHabitacion,

        @Schema(description = "Tipo de la habitación", example = "DOBLE")
        @NotNull(message = "El tipo de habitación es requerido")
        @Positive(message = "El id de tipo de habitación debe ser positivo")
        Long idTipoHabitacion,

        @Schema(description = "Precio de la habitación", example = "1500")
        @NotNull(message = "El precio de la habitación es requerido")
        @Positive(message = "El precio de la habitación debe ser positivo")
        @Digits(integer = 8, fraction = 2)
        BigDecimal precio,

        @Schema(description = "Capacidad del cuarto", example = "5")
        @NotNull(message = "La capacidad es requerida")
        @Min(value = 1, message = "La capacidad debe ser mayor o igual a 1")
        Integer capacidad
) { }