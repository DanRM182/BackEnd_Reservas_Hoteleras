package com.reserva_hotel.huespedes.repository;

import com.dan.commons.dto.huespedes.HuespedResponse;
import com.dan.commons.enums.EstadoRegistro;
import com.reserva_hotel.huespedes.entity.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    List<Huesped> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    Boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);

    Boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);

    Boolean existsByDocumentoIgnoreCaseAndEstadoRegistro(String documento, EstadoRegistro estadoRegistro);

    Boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(String email, EstadoRegistro estadoRegistro, Long id);

    Boolean existsByTelefonoAndEstadoRegistroAndIdNot(String telefono, EstadoRegistro estadoRegistro, Long id);

    Boolean existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(String documento, EstadoRegistro estadoRegistro, Long id);
}
