package com.reserva_hotel.huespedes.services;

import com.dan.commons.dto.huespedes.HuespedRequest;
import com.dan.commons.dto.huespedes.HuespedResponse;
import com.dan.commons.service.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {
    HuespedResponse obtenerPorIdSinEstado(Long id);
}
