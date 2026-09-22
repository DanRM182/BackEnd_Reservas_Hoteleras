package com.reservasHotel.reservas.controller;

import com.reservasHotel.commons.controller.CrudController;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import com.reservasHotel.reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController@Validated
@Tag(name="API Reservas", description = "Métodos para la gestión de reservas")
public class ReservaController extends CrudController<ReservaRequest, ReservaResponse, ReservaService> {
    public ReservaController(ReservaService service) {
        super(service);
    }
}
