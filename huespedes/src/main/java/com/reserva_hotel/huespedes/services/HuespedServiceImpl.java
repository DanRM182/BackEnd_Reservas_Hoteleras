package com.reserva_hotel.huespedes.services;

import com.dan.commons.dto.huespedes.HuespedRequest;
import com.dan.commons.dto.huespedes.HuespedResponse;
import com.dan.commons.enums.EstadoRegistro;
import com.dan.commons.exceptions.RecursoNoEncontradoException;
import com.reserva_hotel.huespedes.entity.Huesped;
import com.reserva_hotel.huespedes.mapper.HuespedMapper;
import com.reserva_hotel.huespedes.repository.HuespedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HuespedServiceImpl implements HuespedService {
    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        log.info("Listando todos los huéspedes activos");

        return huespedRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream().map(huespedMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Buscando huésped activo por ID");

        return huespedMapper.entidadAResponse(obtenerHuespedActivo(id));
    }

    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorIdSinEstado(Long id) {
        log.info("Buscando huésped por ID");

        return huespedMapper.entidadAResponse(obtenerHuesped(id));
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        log.info("Registrando nuevo huésped");

        Huesped huesped = huespedMapper.requestAEntidad(request);

        validarDatosUnicos(request);

        huespedRepository.save(huesped);

        log.info("Huésped registrado y con estado activo con ID: {}", huesped.getId());

        return huespedMapper.entidadAResponse(huesped);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = obtenerHuespedActivo(id);

        log.info("Actualizando huésped con id: {}", id);

        validarActualizarDatosUnicos(request, id);

/********************VALIDAR RESERVAS DEL HUESPED***********************************************/

        huesped.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono(),
                request.documento(),
                request.nacionalidad());

        log.info("Huésped con ID actualizado correctamente", id);

        return huespedMapper.entidadAResponse(huesped);
    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = obtenerHuesped(id);

        log.info("Eliminando huésped con ID: {}", id);

        /**************************VALIDAR RESERVAS DEL HUÉSPED**********/

        huesped.eliminar();

        log.info("Huésped con ID {} eliminado correctamente", id);
    }

    private Huesped obtenerHuespedActivo(Long id) {
        log.info("Buscando huésped activo con ID {}", id);

        return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró a un huésped activo con ID " + id));
    }

    private Huesped obtenerHuesped(Long id) {
        log.info("Buscando huésped activo con ID {}", id);

        return huespedRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró a un huésped con ID " + id));
    }

    private void validarDatosUnicos(HuespedRequest request) {
        log.info("Validando unicidad de email, teléfono y documento de huesped activo");

        if(huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(request.email(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("El email ingresado ya está registrado en un huésped con estado activo");

        if(huespedRepository.existsByTelefonoAndEstadoRegistro(request.telefono(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("El teléfono ingresado ya está registrado en un huésped con estado activo");

        if(huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistro(request.documento(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("El documento ingresado ya está registrado en un huésped con estado activo");
    }

    private void validarActualizarDatosUnicos(HuespedRequest request, Long id) {
        log.info("Validando email, teléfono único en actualización");

        if(huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(request.email(),
                EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huésped activo registrado con el correo: "
                    + request.email());

        if(huespedRepository.existsByTelefonoAndEstadoRegistroAndIdNot(request.telefono(),
                EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huésped activo registrado con el teléfono: "
                    + request.email());

        if(huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(request.documento(),
                EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huésped activo registrado con el documento: "
                    + request.email());
    }

}
