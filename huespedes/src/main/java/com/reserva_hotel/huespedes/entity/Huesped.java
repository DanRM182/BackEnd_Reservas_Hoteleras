package com.reserva_hotel.huespedes.entity;

import com.dan.commons.enums.EstadoRegistro;
import com.dan.commons.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "HUESPEDES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED")
    private Long id;

    @Column(name="NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "TELEFONO", length = 10, nullable = false)
    private String telefono;

/********************************************************************/
    @Column(name = "DOCUMENTO", length = 16, nullable = false)
    private String documento;

/************************************************************************/

    @Column(name = "NACIONALIDAD", length = 25, nullable = false)
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    private static void validarDatos (String nombre,String apellidoPaterno,String apellidoMaterno,
                               String email,String telefono,String documento,
                               String nacionalidad)
    {

        StringCustomUtils.validarTamanio(nombre,2,50,
                "El nombre es requerido y debe de contener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno,2,50,
                "El apellido paterno es requerido y debe de contener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno,2,50,
                "El apellido materno y debe de contener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(email,1,100,
                "El email es requerido y debe de tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(telefono,10,10,
                "El telefono es requerido y debe tener exactamente 10 digitos (0-9)");

        StringCustomUtils.validarTamanio(documento,1,25,
                "La nacionalidad  es requerida y debe de tener exactamente 12 caracteres");

        StringCustomUtils.validarTamanio(nacionalidad,1,16,
                "El documento  es requerida y debe de tener exactamente 12 caracteres");
    }

    private void validarNoEliminado() {
        if(this.estadoRegistro == EstadoRegistro.ELIMINADO)
            throw new IllegalStateException("El médico ya está eliminado");
    }

    public void eliminar() {
        validarNoEliminado();

        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email,
                           String telefono, String documento, String nacionalidad) {
        validarNoEliminado();

        validarDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono, documento, nacionalidad);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.trim().toLowerCase();
        this.telefono = telefono.trim();
        this.documento = documento.trim();
        this.nacionalidad = nacionalidad.trim();
    }

    public static Huesped crear(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono,
                   String documento, String nacionalidad) {

        validarDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono, documento, nacionalidad);

        return Huesped.builder()
                .nombre(nombre)
                .apellidoPaterno(apellidoPaterno)
                .apellidoMaterno(apellidoMaterno)
                .email(email)
                .telefono(telefono)
                .documento(documento)
                .nacionalidad(nacionalidad)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }
}
