package com.reservasHotel.reservas.mapper;

import com.reservasHotel.commons.dto.habitacion.DatosHabitacion;
import com.reservasHotel.commons.dto.habitacion.HabitacionResponse;
import com.reservasHotel.commons.dto.huespedes.DatosHuesped;
import com.reservasHotel.commons.dto.huespedes.HuespedResponse;
import com.reservasHotel.commons.mapper.CommonMapper;
import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import com.reservasHotel.reservas.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva> {
    @Override
    public Reserva requestAEntidad(ReservaRequest request) {
        return request != null ?
                Reserva.crear(
                        request.idHabitacion(),
                        request.idHuesped(),
                        request.fechaEntrada(),
                        request.fechaSalida()) : null;
    }

    @Override
    public ReservaResponse entidadAResponse(Reserva entidad) {
        return entidad != null ?
                new ReservaResponse(
                        entidad.getId(),
                        null, null,
                        entidad.getEstadoReserva().getDescripcion(),
                        entidad.getFechaEntrada(),
                        entidad.getFechaSalida()) : null;
    }

    public ReservaResponse entidadAResponse(Reserva entidad, HuespedResponse huesped, HabitacionResponse habitacion) {
        return entidad != null ?
                new ReservaResponse(
                        entidad.getId(),
                        huespedResponseADatosHuesped(huesped),
                        habitacionResponseADatosHabitacion(habitacion),
                        entidad.getEstadoReserva().getDescripcion(),
                        entidad.getFechaEntrada(),
                        entidad.getFechaSalida()) : null;
    }

    private DatosHuesped huespedResponseADatosHuesped(HuespedResponse huesped) {
        return huesped != null ?
                new DatosHuesped(
                        huesped.nombre(),
                        huesped.email(),
                        huesped.telefono(),
                        huesped.documento(),
                        huesped.nacionalidad()) : null;
    }

    private DatosHabitacion habitacionResponseADatosHabitacion(
            HabitacionResponse habitacion) {

        return habitacion != null
                ? new DatosHabitacion(
                habitacion.id(),
                habitacion.numeroHabitacion(),
                habitacion.tipoHabitacion()
        )
                : null;
    }
}
