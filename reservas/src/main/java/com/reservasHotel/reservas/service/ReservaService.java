package com.reservasHotel.reservas.service;

import com.reservasHotel.commons.service.CrudService;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;

public interface ReservaService extends CrudService<ReservaRequest, ReservaResponse> {
    void validarEstadoReservasHuesped(Long idHuesped);
    ReservaResponse actualizarEstado(Long idReserva, Long idEstado);

}