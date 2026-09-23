package com.reservasHotel.commons.enums;

import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor @Getter
public enum TipoHabitacion {
    INDIVIDUAL(1L,"Habitacion estandar"),
    DOBLE(2L, "Habitacion con cama doble"),
    SUITE(3L,"Habitacion de tamaño deluxe");

    private final Long codigo;
    private final String descripcion;

    public static TipoHabitacion obtenerTipoHabitacionPorCodigo(Long codigo) {
        return Arrays.stream(values())
                .filter(tipo -> tipo.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Código de tipo habitación no válido: " + codigo));
    }
}
