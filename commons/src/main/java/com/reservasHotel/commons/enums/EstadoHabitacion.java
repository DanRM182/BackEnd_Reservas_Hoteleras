package com.dan.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum EstadoHabitacion {

    DISPONIBLE("Disponible"),
    OCUPADA("Ocupado"),
    LIMPIEZA("En limpieza"),
    MANTENIMIENTO("En mantenimiento");


    private final String descripcion;

}
