package com.reservasHotel.reservas.service;

import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import com.reservasHotel.commons.utils.ValoresNumericosUtils;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import com.reservasHotel.reservas.entity.Reserva;
import com.reservasHotel.reservas.mapper.ReservaMapper;
import com.reservasHotel.reservas.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservaServiceImpl implements ReservaService {


    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    @Override
    public List<ReservaResponse> listar() {
        log.info("obteniendo reservas");
        return reservaRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(reservaMapper::entidadAResponse).toList();
    }

    @Override
    public ReservaResponse obtenerPorId(Long id) {
        return reservaMapper.entidadAResponse(buscarReservaActiva(id));
    }

    @Override
    public ReservaResponse registrar(ReservaRequest request) {
        return null;
    }

    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    private Reserva buscarReservaActiva(Long id){
        ValoresNumericosUtils.validarLongPositivo(
                id, "El id de la reserva debe de ser positivo y requerido");
        return reservaRepository
                .findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(()-> new RecursoNoEncontradoException(
                        "No se encontro una reserva activa con el id: " + id
                ));

    }

}
