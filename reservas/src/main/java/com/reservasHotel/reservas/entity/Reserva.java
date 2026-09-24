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

        if(!fechaEntrada.isBefore(fechaSalida))
            throw new IllegalArgumentException(("La fecha de entrada debe ser " +
                    "anterior a la fecha de salida"));
    }

    private void validarNoEliminada() {
        if (estadoRegistro==null)
            throw new IllegalStateException("Estado actual del registro invalido");
        if(this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalStateException("La reserva ya está eliminada");
    }

    private void validarCambioEstado(EstadoReserva nuevoEstado){
        validarNoEliminada();
        if (estadoReserva==null)
            throw new IllegalStateException("la transicion no esta permitida");
        if (nuevoEstado ==null)
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo");
        if (!this.estadoReserva.puedeCambiarA(nuevoEstado))
            throw new IllegalStateException("No se permite cambiar de " + this.estadoReserva + " a " + nuevoEstado);
    }

    private static void validarDatos(Long idHabitacion, Long idHuesped, LocalDate fechaEntrada,
                   LocalDate fechaSalida) {
        validarId(idHabitacion, "habitación");

        validarId(idHuesped, "huésped");

        validarRangoFechas(fechaEntrada, fechaSalida);
    }

    public void realizarCheckIn(){
        validarCambioEstado(EstadoReserva.EN_CURSO);
        this.estadoReserva=EstadoReserva.EN_CURSO;
    }
    public void realizarCheckOut(){
        validarCambioEstado(EstadoReserva.FINALIZADA);
        this.estadoReserva=EstadoReserva.FINALIZADA;
    }
    public void cancelar(){
        validarCambioEstado(EstadoReserva.CANCELADA);
        this.estadoReserva=EstadoReserva.CANCELADA;
    }


    public void actualizarFechas(LocalDate nuevaEntrada, LocalDate nuevaSalida){
        validarNoEliminada();

        if (this.estadoReserva != EstadoReserva.CONFIRMADA)
            throw new IllegalStateException("No se permite modificas ambas fechas");

        validarRangoFechas(nuevaEntrada,nuevaSalida);
        if (nuevaEntrada.isBefore(LocalDate.now()))
                throw new IllegalArgumentException("La fecha de entrada debe ser hoy o pestrior");

            this.fechaEntrada = nuevaEntrada;
            this.fechaSalida = nuevaSalida;

    }

    public void actualizarFechaSalida(LocalDate nuevaSalida){
        validarNoEliminada();
        if (estadoReserva==null)
            throw new IllegalStateException("El estado de la reserva no puede ser nula ");
        if (!this.estadoReserva.isSalidaActualizable())
            throw new IllegalStateException("no se puede actualizar las fechas");
        validarRangoFechas(this.fechaEntrada,nuevaSalida);
        if (nuevaSalida.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("la fecha debe ser hoy o posterior");
        this.fechaSalida=nuevaSalida;

    }

    public static Reserva crear(Long idHabitacion, Long idHuesped, LocalDate fechaEntrada,
                                LocalDate fechaSalida) {
        validarDatos(idHabitacion, idHuesped, fechaEntrada, fechaSalida);

        if (fechaEntrada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de entrada debe ser hoy o posterior"
            );
        }

        return Reserva.builder()
                .idHabitacion(idHabitacion)
                .idHuesped(idHuesped)
                .fechaEntrada(fechaEntrada)
                .fechaSalida(fechaSalida)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .build();
    }

    public void eliminar (){
        validarNoEliminada();

        if (this.estadoReserva== null)
            throw new IllegalStateException("la reserva no tiene un estado valido");
        if (this.estadoReserva==EstadoReserva.EN_CURSO)
            throw new IllegalStateException("no se puede eliminar una reserva en curso");

        this.estadoRegistro=EstadoRegistro.ELIMINADO;
    }
}
