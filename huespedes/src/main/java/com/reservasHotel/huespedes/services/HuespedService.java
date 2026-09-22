package com.reservasHotel.huespedes.services;

import com.reservasHotel.commons.dto.huespedes.HuespedRequest;
import com.reservasHotel.commons.dto.huespedes.HuespedResponse;
import com.reservasHotel.commons.service.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {
    HuespedResponse obtenerPorIdSinEstado(Long id);
}
