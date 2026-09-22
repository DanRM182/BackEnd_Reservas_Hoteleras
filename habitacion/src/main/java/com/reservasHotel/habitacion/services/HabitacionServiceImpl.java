package com.reservasHotel.habitacion.services;

import com.dan.commons.dto.habitacion.HabitacionRequest;
import com.dan.commons.dto.habitacion.HabitacionResponse;
import com.dan.commons.enums.EstadoHabitacion;
import com.dan.commons.enums.EstadoRegistro;
import com.dan.commons.exceptions.RecursoNoEncontradoException;
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
    public HabitacionResponse actualizarEstado(Long id, EstadoHabitacion nuevoEstado) {
        Habitacion habitacion = obtenerHabitacionActivaPorId(id);
        habitacion.actualizarEstado(nuevoEstado);
        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HabitacionResponse> listar() {
        log.info("Listando las habitaciones");
        return habitacionRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(habitacionMapper::entidadAResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("obteniendo habitacion...");
        return habitacionMapper.entidadAResponse(obtenerHabitacionActivaPorId(id));
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando datos de habitacion");
        validarDatosUnicos(request,null);
        Habitacion habitacion=habitacionMapper.requestAEntidad(request);
        Habitacion guuarda = habitacionRepository.save(habitacion);
        return habitacionMapper.entidadAResponse(guuarda);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = obtenerHabitacionActivaPorId(id);
        String numeroHabitacion=request.numeroHabitacion().trim();
        if (!numeroHabitacion.equals(habitacion.getNumeroHabitacion()))
            validarDatosUnicos(request,id);
        habitacion.actualizar(
                numeroHabitacion,
                request.precio(),
                request.capacidad()
        );
        habitacion.actualizarTipo(request.tipo());
        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Override
    public void eliminar(Long id) {
        Habitacion habitacion =obtenerHabitacionActivaPorId(id);
        habitacion.eliminar();

    }

    private Habitacion obtenerHabitacionActivaPorId(Long id){
        log.info("Buscando habitacion con id {}", id);
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(()-> new RecursoNoEncontradoException("Habitacion activa no encontrada con id:" + id));

    }
    private void  validarDatosUnicos (HabitacionRequest request, Long idExcluir){
        String numeroHabitacion=request.numeroHabitacion().trim();
        boolean existeDuplicado = idExcluir== null
                ?
                habitacionRepository.existsByNumeroHabitacionAndEstadoRegistro(
                        numeroHabitacion,EstadoRegistro.ACTIVO
                )
                :
                habitacionRepository.existsByNumeroHabitacionAndEstadoRegistroAndIdNot(
                        numeroHabitacion,
                        EstadoRegistro.ACTIVO,
                        idExcluir);
        if (existeDuplicado)
            throw new IllegalStateException("Ya existe una habitacion activa con ese numerto " + numeroHabitacion);

    }
}
