package com.reservasHotel.reservas.enums;

import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum EstadoReserva {
    CONFIRMADA(1L, "Reservación creada", true, true, true, true) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return EnumSet.of(EN_CURSO, CANCELADA);
        }
    },
    EN_CURSO(2L, "Check-in realizado", false, true, false, false) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return EnumSet.of(FINALIZADA);
        }
    },
    FINALIZADA(3L, "Check-out realizado", false, false, false, false) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return Set.of();
        }
    },
    CANCELADA(4L, "Reserva cancelada", false, false, false, false) {
        @Override
        public Set<EstadoReserva> puedeCambiar() {
            return Set.of();
        }
    };

    private final Long codigo;
    private final String descripcion;
    private final boolean entradaActualizable;
    private final boolean salidaActualizable;
    private final boolean habitacionActualizable;
    private final boolean cancelable;

    public abstract Set<EstadoReserva> puedeCambiar();

    public boolean puedeCambiarA(EstadoReserva nuevoEstado) {
        return puedeCambiar().contains(nuevoEstado);
    }

    public static EstadoReserva obtenerEstadoReservaPorCodigo(Long codigo) {
        for(EstadoReserva e: values()) {
            if(Objects.equals(e.codigo, codigo))
                return e;
        }

        throw new RecursoNoEncontradoException("Código de reserva no válido: " + codigo);
    }
}
