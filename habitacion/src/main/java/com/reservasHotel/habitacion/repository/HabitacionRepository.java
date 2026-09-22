package com.reservasHotel.habitacion.repository;

import com.dan.commons.enums.EstadoRegistro;
import com.reservasHotel.habitacion.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository  extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);
    Optional<Habitacion> findByIdAndEstadoRegistro(Long id ,EstadoRegistro estadoRegistro);
    boolean existsByNumeroHabitacionAndEstadoRegistro(String numeroHabitacion, EstadoRegistro estadoRegistro);
    boolean existsByNumeroHabitacionAndEstadoRegistroAndIdNot(String numeroHabitacion,EstadoRegistro estadoRegistro, Long id);


}
