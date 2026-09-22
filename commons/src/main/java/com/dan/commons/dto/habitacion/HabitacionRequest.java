package com.dan.commons.dto.habitacion;

import com.dan.commons.enums.TipoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
@Schema(description = "Datos necesarios para registrar o actualizar una habitacion")
public record HabitacionRequest(
        @Schema(description = "Numero de la habitacion", example = "101A")
        @NotBlank
        @Size(max = 20)
        @Pattern(regexp = "^[1-9][0-9]*[A-Z]?$")
    String numeroHabitacion,
        @Schema(description = "Tipo de la habitacion", example = "DOBLE")
    @NotNull
    TipoHabitacion tipo,
        @Schema(description = "Precio de la habitacion", example = "1500")
    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    BigDecimal precio,
        @Schema(description = "capacidad del cuarto", example = "5")
    @NotNull
    @Min(1)
    Integer capacidad
) {
}
