package com.reservasHotel.habitacion.repository;

import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.habitacion.entity.Habitacion;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository  extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Habitacion> findByIdAndEstadoRegistro(Long id ,EstadoRegistro estadoRegistro);

    Optional<Habitacion> findById(Long id);

    boolean existsByNumeroHabitacionAndEstadoRegistro(String numeroHabitacion, EstadoRegistro estadoRegistro);

    boolean existsByNumeroHabitacionAndEstadoRegistroAndIdNot(String numeroHabitacion,EstadoRegistro estadoRegistro, Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Habitacion> findWithLockByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
}
