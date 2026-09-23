package com.reservasHotel.reservas.repository;

import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.reservas.entity.Reserva;
import com.reservasHotel.reservas.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Reserva> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
    List<Reserva>findAllByEstadoRegistro(EstadoRegistro estadoRegistro);

    boolean existsByIdHuespedAndEstadoReservaIn(Long idHuesped, Collection<EstadoReserva> estadoReservas);
}
