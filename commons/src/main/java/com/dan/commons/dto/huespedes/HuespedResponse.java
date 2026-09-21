package com.dan.commons.dto.huespedes;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un huésped")
public record HuespedResponse(
        @Schema(description = "Identificador único del huésped", example = "1")
        Long id,

        @Schema(description = "Nombre completo del huésped", example = "Carlos Ramírez López")
        String nombre,

        @Schema(description = "Email del huésped", example = "test@test.com")
        String email,

        @Schema(description = "Número telefónico del huésped", example = "7771234567")
        String telefono,

        @Schema(description = "Documento de identificación presentado por el huésped", example = "INE")
        String documento,

        @Schema(description = "Nacionalidad del huésped", example = "Mexicano")
        String nacionalidad
) { }