package com.reservasHotel.reservas.service;

import com.reservasHotel.commons.clients.HabitacionClient;
import com.reservasHotel.commons.clients.HuespedClient;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.commons.dto.huespedes.HuespedResponse;
import com.reservasHotel.commons.enums.EstadoHabitacion;
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
    private final HabitacionClient habitacionClient;

    @Override
    public List<ReservaResponse> listar() {
        log.info("Listando todas las reservas activas");

        return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream().map(this::obtenerRespuestaCompleta).peek(n -> log.info("IDS reservas: {}",n.id()))
                .toList();
    }


    @Override
    public ReservaResponse obtenerPorId(Long id) {
        return obtenerRespuestaCompleta(buscarReservaActiva(id));
    }


    @Override
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Registrando nueva reserva...");

        //TRAER HABITACIÓN

        HuespedResponse huesped = obtenerHuespedActivo(request.idHuesped());

        HabitacionResponse habitacion = obtenerHabitacionActiva(request.idHabitacion());

        //VALIDACIONES
        validarHabitacionDisponible(habitacion);

        Reserva reserva = reservaMapper.requestAEntidad(request);

        reservaRepository.save(reserva);

        //cambiar estado habitación
        habitacion = habitacionClient.ocuparPorReserva(request.idHabitacion());


        log.info("Reserva registrada exitosamente");

        return reservaMapper.entidadAResponse(
                reserva,
                huesped,habitacion
        );
    }

    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        Reserva reserva= buscarReservaActiva(id);

        validarDatosNoModificables(reserva,request);

        aplicarActualizacionFechas(reserva,request);


        return obtenerRespuestaCompleta(reserva);
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
    public ReservaResponse actualizarEstado(Long idReserva, Long idEstado) {
        Reserva reserva = buscarReservaActiva(idReserva);

        EstadoReserva nuevoEstado = EstadoReserva.obtenerEstadoReservaPorCodigo(idEstado);

        HuespedResponse huesped = obtenerHuespedSinEstado(reserva.getIdHuesped());

        HabitacionResponse habitacion = aplicarCambioEstado(reserva, nuevoEstado);

        return reservaMapper.entidadAResponse(reserva,huesped,habitacion);
    }

    @Override
    public void eliminar(Long id) {

        Reserva reserva = buscarReservaActiva(id);

        reserva.eliminar();

        if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA)
            habitacionClient.liberarPorReserva(reserva.getIdHabitacion());

    }

    private HabitacionResponse obtenerHabitacionActiva(Long id){

        return validarObjetoRecibido(
                id,
                habitacionClient::obtenerHabitacionActivaPorId,
                "habitacion activa no encontrada con id: " + id
        );
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

    private void validarHabitacionDisponible(HabitacionResponse habitacion){

        if (!EstadoHabitacion.DISPONIBLE.getDescripcion()
                .equals(habitacion.estadoHabitacion()))
            throw new IllegalStateException("La habitacion no esta disponible para reserva");

    }


    private HabitacionResponse obtenerHabitacionSinEstado(Long id){

        return validarObjetoRecibido(
                id,
                habitacionClient::obtenerHabitacionPorIdSinEstado, "Habitacion no encontrada por su id: " + id);

    }


    private HabitacionResponse aplicarCambioEstado(Reserva reserva, EstadoReserva nuevoEstado){

        return switch (nuevoEstado){
            case EN_CURSO -> {reserva.realizarCheckIn();
                yield obtenerHabitacionActiva(reserva.getIdHabitacion());
            }
            case FINALIZADA -> {reserva.realizarCheckOut();
                yield habitacionClient.liberarPorReserva(reserva.getIdHabitacion());
            }
            case CANCELADA -> {reserva.cancelar();
                yield habitacionClient.liberarPorReserva(reserva.getIdHabitacion());
            }
            default -> throw new IllegalArgumentException("no se permite cambiar la reserva de ese estado");

        };
    }


    private void validarDatosNoModificables(Reserva reserva, ReservaRequest request){
        if (!reserva.getIdHuesped().equals(request.idHuesped())
        || !reserva.getIdHabitacion().equals(request.idHabitacion()))
            throw new IllegalArgumentException("no se permite cambiar la habitacion ni el huesped");

    }

    private void aplicarActualizacionFechas(Reserva reserva, ReservaRequest request){
        switch (reserva.getEstadoReserva()){
            case CONFIRMADA -> reserva.actualizarFechas(
                    request.fechaEntrada(),
                    request.fechaSalida());
            case EN_CURSO -> {
                if (!reserva.getFechaEntrada().equals(request.fechaEntrada()))
                    throw new IllegalArgumentException("no se puede modificar la entrada de una reserva en curso");
                reserva.actualizarFechaSalida(request.fechaSalida());
            }
            default -> throw new IllegalStateException("no se puede actualizar una reseva cancelada o finalizada");

        }


    }

    private ReservaResponse obtenerRespuestaCompleta(Reserva reserva) {
        return reservaMapper.entidadAResponse(
                reserva,
                obtenerHuespedSinEstado(reserva.getIdHuesped()),
                obtenerHabitacionSinEstado(reserva.getIdHabitacion())
        );
    }

}
