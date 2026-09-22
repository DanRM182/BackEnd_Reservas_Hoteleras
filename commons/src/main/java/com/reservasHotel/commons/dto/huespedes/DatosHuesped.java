package com.reservasHotel.commons.dto.huespedes;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un huésped asociado a una reserva")
public record DatosHuesped(
        @Schema(description = "Nombre copmleto del huésped", example = "Ricardo Flores Durán")
        String nombre,

        @Schema(description = "Email de contacto del huésped", example = "lolo@lolo.com")
        String email,

        @Schema(description = "Teléfono de contacto del huésped", example = "1234567890")
        String telefono,

        @Schema(description = "Documento de identificación presentado por el huésped", example = "INE")
        String documento,

        @Schema(description = "Nacionalidad del huésped", example = "Mexicano")
        String nacionalidad
) { }