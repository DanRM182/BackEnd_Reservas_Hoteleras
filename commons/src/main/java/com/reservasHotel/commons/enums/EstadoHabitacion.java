package com.reservasHotel.commons.enums;

import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor @Getter
public enum EstadoHabitacion {

    DISPONIBLE(1L,"Disponible"),
    OCUPADA(2L,"Ocupado"),
    LIMPIEZA(3L, "En limpieza"),
    MANTENIMIENTO(4L,"En mantenimiento");

    private final Long codigo;
    private final String descripcion;

    public static EstadoHabitacion obtenerEstadoHabitacionPorCodigo(Long codigo) {
        return Arrays.stream(values())
                .filter(estado -> estado.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Código de estado habitación no válido: " + codigo));
    }
}
