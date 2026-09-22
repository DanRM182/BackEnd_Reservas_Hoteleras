package com.reservasHotel.reservas.entity;

import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.utils.ValoresNumericosUtils;
import com.reservasHotel.reservas.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long id;

    @Column(name = "ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ESTADO_RESERVA", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "ESTADO_REGISTRO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;

    private static void validarId(Long id, String campo) {
        ValoresNumericosUtils.validarLongPositivo(id,
                "El id de " + campo + " es requerido y debe ser positivo");
    }

    private static void validarRangoFechas(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if(fechaEntrada == null)
            throw new IllegalArgumentException("La fecha de entrada es requerida");

        if(fechaSalida == null)
            throw new IllegalArgumentException("La fecha de salida es requerida");

        if(!fechaEntrada.isAfter(fechaSalida))
            throw new IllegalArgumentException(("La fecha de entrada debe ser " +
                    "anterior a la fecha de salida"));
    }

    private void validarNoEliminada() {
        if(this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalArgumentException("La reserva ya está eliminada");
    }

    private static void validarDatos(Long idHabitacion, Long idHuesped, LocalDate fechaEntrada,
                   LocalDate fechaSalida) {
        validarId(idHabitacion, "habitación");

        validarId(idHuesped, "huésped");

        validarRangoFechas(fechaEntrada, fechaSalida);
    }

    public static Reserva crear(Long idHabitacion, Long idHuesped, LocalDate fechaEntrada,
                                LocalDate fechaSalida) {
        validarDatos(idHabitacion, idHuesped, fechaEntrada, fechaSalida);

        return Reserva.builder()
                .idHabitacion(idHabitacion)
                .idHuesped(idHuesped)
                .fechaEntrada(fechaEntrada)
                .fechaSalida(fechaSalida)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .build();
    }
}
