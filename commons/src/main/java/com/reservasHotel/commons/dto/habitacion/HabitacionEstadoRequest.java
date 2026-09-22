package com.reservasHotel.commons.dto.habitacion;

import com.reservasHotel.commons.enums.EstadoHabitacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Datos para cambiar manualmente el estado de una habitación")
public record HabitacionEstadoRequest(
                @NotNull(message = "El estado es requerido")
                @Schema(description = "Nuevo estado de la habitación", example ="MANTENIMIENTO")
        EstadoHabitacion estado

) {
}
