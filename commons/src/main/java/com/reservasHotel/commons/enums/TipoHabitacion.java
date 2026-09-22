package com.reservasHotel.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum TipoHabitacion {
    INDIVIDUAL(1L,"Habitacion estandar"),
    DOBLE(2L, "Habitacion con cama doble"),
    SUITE(3L,"Habitacion de tamaño deluxe");

    private final Long codigo;
    private final String descripcion;
}
