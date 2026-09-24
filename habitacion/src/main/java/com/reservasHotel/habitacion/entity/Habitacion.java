package com.reservasHotel.habitacion.entity;

import com.reservasHotel.commons.enums.EstadoHabitacion;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.enums.TipoHabitacion;
import com.reservasHotel.commons.utils.StringCustomUtils;
import com.reservasHotel.commons.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "HABITACION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @Column(name = "NUMERO_HABITACION", length = 20, nullable = false)
    private  String numeroHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_HABITACION", length = 20, nullable = false)
    private TipoHabitacion tipoHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", length = 30, nullable = false)
    private EstadoHabitacion estadoHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", length = 10, nullable = false)
    private EstadoRegistro estadoRegistro;

    @Column(name = "PRECIO", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    private static void validarDatos(String numeroHabitacion, BigDecimal precio, TipoHabitacion tipoHabitacion, Integer capacidad){
        StringCustomUtils.validarTamanio(numeroHabitacion,1,20,"El número de la habitacion tiene que contener de 1 a 20 caracteres");

        ValoresNumericosUtils.validarBigDecimalPositivo(precio,"El precio debe de ser positivo");

        if(tipoHabitacion == null)
            throw new IllegalArgumentException("El tipo de habitación es requerido");

        ValoresNumericosUtils.validarEnteroPositivo(capacidad,"La capacidad deb de ser positiva");
    }

    private void validarNoEliminado(){
        if (this.estadoRegistro == null)
            throw new IllegalStateException("La habitación no tiene estado de registro");

        if (this.estadoRegistro==EstadoRegistro.ELIMINADO)
            throw new IllegalStateException("La habitacion ya esta eliminada");
    }
    public void eliminar(){
        validarNoEliminado();
        if (this.estadoHabitacion == EstadoHabitacion.OCUPADA)
            throw new IllegalStateException("No se puede eliminar habitacion ocupada");

        this.estadoRegistro=EstadoRegistro.ELIMINADO;
    }

    public void actualizarTipo(TipoHabitacion tipo){
        validarNoEliminado();

        if (tipo==null)
            throw new IllegalArgumentException("El tipo de habitación es requerido");

        this.tipoHabitacion = tipo;
    }

    public void actualizarEstado(EstadoHabitacion nuevoEstado){
        validarNoEliminado();
        if (nuevoEstado==null)
            throw new IllegalArgumentException("El estado es requerido");

        if (this.estadoHabitacion == null)
            throw new IllegalStateException("La habitacion no tiene un estado válido");

        if (this.estadoHabitacion==EstadoHabitacion.OCUPADA && nuevoEstado == EstadoHabitacion.DISPONIBLE)
            throw new IllegalStateException("No se puede cambiar manualmente el estado " +
                    "de una habitacion ocupada a disponible");

        if (nuevoEstado == EstadoHabitacion.OCUPADA)
            throw new IllegalStateException("La habitacion solo se puede ocuparse mediante una reserva");

        this.estadoHabitacion = nuevoEstado;
    }

    public void actualizar(String numeroHabitacion, BigDecimal precio, Integer capacidad,
                           TipoHabitacion tipoHabitacion){
        validarNoEliminado();

        validarDatos(numeroHabitacion,precio, tipoHabitacion,capacidad);

        this.numeroHabitacion=numeroHabitacion.trim();
        this.precio=precio;
        this.capacidad=capacidad;
        this.tipoHabitacion = tipoHabitacion;
    }

    public static Habitacion crear(String numeroHabitacion, TipoHabitacion tipoHabitacion,
                      BigDecimal precio, Integer capacidad) {
        validarDatos(numeroHabitacion, precio, tipoHabitacion, capacidad);

        return Habitacion.builder()
                .numeroHabitacion(numeroHabitacion)
                .tipoHabitacion(tipoHabitacion)
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .precio(precio)
                .capacidad(capacidad)
                .build();
    }

    //comprueba que no este eliminada
    //rechaza si esta en otros estados-solo debe de ser disponible
    //si esta disponible cambia su estado a ocupada
    public void ocuparPorReserva(){
        validarNoEliminado();
        if (this.estadoHabitacion != EstadoHabitacion.DISPONIBLE)
            throw new IllegalStateException("La habitacion debe estar disponible par reserva");
        this.estadoHabitacion=EstadoHabitacion.OCUPADA;
    }


    public void liberarPorReserva(){
        validarNoEliminado();

        if (this.estadoHabitacion != EstadoHabitacion.OCUPADA)
            throw new IllegalStateException("La habitacion debe de estar ocupada para liberarla");

        this.estadoHabitacion=EstadoHabitacion.DISPONIBLE;
    }

    /*
    public void ocuparPorReserva(Long idReserva){
        validarNoEliminado();
        ValoresNumericosUtils.validarLongPositivo(idReserva, "El id de reserva es requerido y debe ser posuitivo");
        if (this.estado== EstadoHabitacion.OCUPADA && idReserva.equals(this.idReservaActual))
            return;
        if (this.estado != EstadoHabitacion.DISPONIBLE || this.idReservaActual != null)
            throw new IllegalStateException("La habitacion no esta disponible para asignar reserva");
        this.idReservaActual=idReserva;
        this.estado=EstadoHabitacion.OCUPADA;
    }
*
    public void liberarPorReserva(Long idReserva){
        validarNoEliminado();
        ValoresNumericosUtils.validarLongPositivo(idReserva, "El id de reserva debe de ser positivo");
        if (this.estado != EstadoHabitacion.OCUPADA)
            throw new IllegalStateException("Solo se puede liberar una habitacion ocupada");
        if (!idReserva.equals(this.idReservaActual))
            throw new IllegalStateException("La habitacion no esta ocupada por la reserva indicada");
        this.idReservaActual=null;
        this.estado=EstadoHabitacion.DISPONIBLE;

    }
*/

}
