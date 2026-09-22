package com.reservasHotel.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservas")
public interface ReservaClient {
    @GetMapping("{idHuesped}/estadoReservasHuesped")
    void validarEstadoReservasHuesped(
            @PathVariable Long idHuesped);
}
