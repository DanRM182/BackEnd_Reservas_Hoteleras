package com.reservasHotel.commons.dto.huespedes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para guardar/modificar un huésped")
public record HuespedRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        @Schema(description = "Nombre del huésped", example = "Juan Gabriel")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
        @Schema(description = "Apellido paterno del huésped", example = "Gónzales")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
        @Schema(description = "Apellido materno del huésped", example = "Pérez")
        String apellidoMaterno,

        @Schema(description = "Correo electrónico del huésped", example = "carlos.ramirez@example.com")
        @NotBlank(message = "El email es requerido")
        @Email(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "El formato del correo electrónico no es válido"
        )
        @Size(min = 8,max = 100, message = "El email debe tener entre 8 y 100 caracteres")
        String email,

        @Schema(description = "Número telefónico del huésped de 10 dígitos", example = "7771234567")
        @NotBlank(message = "El teléfono es requerido")
        @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
        String telefono,

        @NotBlank(message = "El documento es requerido")
        @Size(min = 1, max = 25, message = "El documento debe tener entre 1 y 25 caracteres")
        @Schema(description = "Documento presentado para identificación por el huésped", example = "INE")
        String documento,

        @NotBlank(message = "La nacionalidad es requerido")
        @Size(min = 1, max = 25, message = "La nacionalidad debe tener entre 1 y 25 caracteres")
        @Schema(description = "Nacionalidad del huésped", example = "Mexicano")
        String nacionalidad
) { }