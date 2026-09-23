package com.reservasHotel.habitacion.services;

import com.reservasHotel.commons.dto.habitacion.HabitacionRequest;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.commons.enums.EstadoHabitacion;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.enums.TipoHabitacion;
import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import com.reservasHotel.commons.utils.ValoresNumericosUtils;
import com.reservasHotel.habitacion.entity.Habitacion;
import com.reservasHotel.habitacion.mapper.HabitacionMapper;
import com.reservasHotel.habitacion.repository.HabitacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {
    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando las habitaciones activas");

        return habitacionRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(habitacionMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("Obteniendo habitacion activa por ID...");

        return habitacionMapper.entidadAResponse(obtenerHabitacionActivaPorId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorIdSinEstado(Long id) {
        log.info("Obteniendo habitacion por ID...");

        return habitacionMapper.entidadAResponse(obtenerHabitacionPorId(id));
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando datos de habitación");

        Habitacion habitacion = habitacionMapper.requestAEntidad(request);

        validarDatosUnicos(request);

        habitacionRepository.save(habitacion);

        log.info("Habitación registrada y con estado activo con id: {}",
                habitacion.getId());

        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = obtenerHabitacionActivaPorId(id);

        log.info("Actualizando habitación con id: {}", id);

        validarDatosUnicosActualizar(request, id);

        validarEstadoModificable(habitacion);

        habitacion.actualizar(
                request.numeroHabitacion(),
                request.precio(),
                request.capacidad(),
                TipoHabitacion.obtenerTipoHabitacionPorCodigo(request.idTipoHabitacion()));

        log.info("Habitación actualizada correctamente");

        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizarEstado(Long id, Long idEstado) {
        Habitacion habitacion = obtenerHabitacionActivaPorId(id);

        log.info("Actualizando estado de habitación con id: {}", id);

        if(EstadoHabitacion.DISPONIBLE.getCodigo().equals(idEstado)
        && habitacion.getEstadoHabitacion().equals(EstadoHabitacion.OCUPADA))
            throw new IllegalStateException("No se puede cambiar manualmente " +
                    "el estado de la habitación de OCUPADA a DISPONIBLE");


        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Override
    public void eliminar(Long id) {
        Habitacion habitacion =obtenerHabitacionPorId(id);

        log.info("Eliminando habitación con ID: ", id);

        validarEstadoModificable(habitacion);

        habitacion.eliminar();

        log.info("Habitación con ID {} eliminada correctamente", id);
    }

    private Habitacion obtenerHabitacionActivaPorId(Long id){
        log.info("Buscando habitación con id {}", id);
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(()-> new RecursoNoEncontradoException("Habitacion activa no encontrada con id:" + id));
    }

    private Habitacion obtenerHabitacionPorId(Long id){
        log.info("Buscando habitación con id {}", id);
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(()-> new RecursoNoEncontradoException("Habitacion activa no encontrada con id:" + id));
    }

    private void validarDatosUnicos(HabitacionRequest request) {
        log.info("Validando unicidad de número de habitación");

        if(habitacionRepository.existsByNumeroHabitacionAndEstadoRegistro(
                request.numeroHabitacion(), EstadoRegistro.ACTIVO)) {
            throw new IllegalArgumentException("El número de habitación ya existe en " +
                    "otra habitación activa");
        }
    }

    private void validarDatosUnicosActualizar(HabitacionRequest request, Long id) {
        log.info("Validando unicidad de número de habitación al actualizar");

        if(habitacionRepository.existsByNumeroHabitacionAndEstadoRegistroAndIdNot(
                request.numeroHabitacion(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalArgumentException("El número de habitación ya existe en " +
                    "otra habitación activa");
        }
    }

    private void validarEstadoModificable(Habitacion habitacion) {
        if(habitacion.getEstadoHabitacion().equals(EstadoHabitacion.OCUPADA))
            throw new IllegalArgumentException("La habitación con id "
            + habitacion.getId() + " tiene estado OCUPADA");
    }
}
