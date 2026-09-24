package com.reservasHotel.reservas.service;

import com.reservasHotel.commons.service.CrudService;
import com.reservasHotel.reservas.dto.ReservaFechasRequest;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import com.reservasHotel.reservas.dto.ReservaSalidaRequest;

public interface ReservaService extends CrudService<ReservaRequest, ReservaResponse> {
    void validarEstadoReservasHuesped(Long idHuesped);
    ReservaResponse actualizarEstado(Long idReserva, Long idEstado);
    ReservaResponse actualizarFechas(Long id, ReservaFechasRequest request);
    ReservaResponse actualizarFechaSalida(Long id, ReservaSalidaRequest request);

}