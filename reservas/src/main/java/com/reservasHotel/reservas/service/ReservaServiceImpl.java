package com.reservasHotel.reservas.service;

import com.reservasHotel.commons.clients.HuespedClient;
import com.reservasHotel.commons.dto.huespedes.HuespedResponse;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.exceptions.EntidadRelacionadaException;
import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import com.reservasHotel.commons.enums.EstadoRegistro;
import com.reservasHotel.commons.exceptions.RecursoNoEncontradoException;
import com.reservasHotel.commons.utils.ValoresNumericosUtils;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import com.reservasHotel.reservas.entity.Reserva;
import com.reservasHotel.reservas.enums.EstadoReserva;
import com.reservasHotel.reservas.mapper.ReservaMapper;
import com.reservasHotel.reservas.repository.ReservaRepository;
import feign.FeignException;
import com.reservasHotel.reservas.entity.Reserva;
import com.reservasHotel.reservas.mapper.ReservaMapper;
import com.reservasHotel.reservas.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final HuespedClient huespedClient;

    @Override
    public List<ReservaResponse> listar() {
        log.info("Listando todas las reservas activas");

        return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream().map(reserva -> reservaMapper.entidadAResponse(
                        reserva,
                        obtenerHuespedSinEstado(reserva.getIdHuesped()),
                        null
                )).toList();
    }

    @Override
    public ReservaResponse obtenerPorId(Long id) {
        return reservaMapper.entidadAResponse(buscarReservaActiva(id));
    }

    @Override
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Registrando nueva reserva...");

        //TRAER HABITACIÓN

        HuespedResponse huesped = obtenerHuespedActivo(request.idHuesped());

        //VALIDACIONES

        Reserva reserva = reservaMapper.requestAEntidad(request);

        reservaRepository.save(reserva);

        //cambiar estado habitación

        log.info("Reserva registrada exitosamente");

        return reservaMapper.entidadAResponse(
                reserva,
                huesped,null
        );
    }

    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        return null;
    }


    @Override
    public void validarEstadoReservasHuesped(Long idHuesped) {
        log.info("Validando si el huésped con id {} tiene reservas con estado EN_CURSO",
                idHuesped);

        validarReservaActiva(idHuesped, List.of(EstadoReserva.EN_CURSO),
                "El huésped tiene reserva con estado EN_CURSO",
                reservaRepository::existsByIdHuespedAndEstadoReservaIn);
    }

    @Override
    public void eliminar(Long id) {

    }

    private HuespedResponse obtenerHuespedSinEstado(Long id) {
        log.info("Buscando huésped sin estado con id {} en el servicio remoto", id );

        return validarObjetoRecibido(id, huespedClient::obtenerHuespedPorIdSinEstado,
                "Huésped no encontrado con id: " + id);
    }

    private HuespedResponse obtenerHuespedActivo(Long id) {
        log.info("Buscando huésped activo con id {} en el servicio remoto", id);

        return validarObjetoRecibido(id, huespedClient::obtenerHuespedActivoPorId,
                "Huésped activo no encontrado con id: " + id);
    }


    private <T, R> R validarObjetoRecibido(T objeto, Function<T, R> obtenerObjeto, String mensaje) {
        try {
            return obtenerObjeto.apply(objeto);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(mensaje);
        }
    }

    private void validarReservaActiva(Long id, List<EstadoReserva> estadosReserva, String mensaje,
                                      BiFunction<Long, List<EstadoReserva>, Boolean> existeReserva) {
        log.info("Validando se existe reserva activa...");

        if(existeReserva.apply(id, estadosReserva))
            throw new EntidadRelacionadaException(mensaje);
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
