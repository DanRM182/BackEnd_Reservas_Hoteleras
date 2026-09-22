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
    private TipoHabitacion tipo;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", length = 30, nullable = false)
    private EstadoHabitacion estado;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", length = 10, nullable = false)
    private EstadoRegistro estadoRegistro;
    @Column(name = "PRECIO", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;
    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;


    private void validarDatos(String numeroHabitacion, BigDecimal precio, Integer capacidad){
        StringCustomUtils.validarTamanio(numeroHabitacion,1,20,"El nombre de la habitacion tiene que contener de 1 a 20 caracteres");
        ValoresNumericosUtils.validarBigDecimalPositivo(precio,"El precio debe de estar acorde a las cantidades a ingresar");
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
        if (this.estado==EstadoHabitacion.OCUPADA)
            throw new IllegalStateException("No se puede eliminar habitacion ocupada");
        this.estadoRegistro=EstadoRegistro.ELIMINADO;
    }

    public void actualizarTipo(TipoHabitacion tipo){
        validarNoEliminado();
        if (tipo==null)
            throw new IllegalArgumentException("El tipo es requerido");
        this.tipo=tipo;
    }
    public void actualizarEstado(EstadoHabitacion nuevoEstado){
        validarNoEliminado();
        if (nuevoEstado==null)
            throw new IllegalArgumentException("El estado es requerido");
        if (this.estado==EstadoHabitacion.OCUPADA && nuevoEstado==EstadoHabitacion.DISPONIBLE)
            throw new IllegalStateException("No se puede cambiar manualmente una habitacion ocupada a disponible");
        this.estado=nuevoEstado;
    }

    public void actualizar(String numeroHabitacion, BigDecimal precio, Integer capacidad){
        validarNoEliminado();
        validarDatos(numeroHabitacion,precio,capacidad);
        this.numeroHabitacion=numeroHabitacion.trim();
        this.precio=precio;
        this.capacidad=capacidad;
    }


}
