package com.reserva_hotel.huespedes.mapper;

import com.dan.commons.mapper.CommonMapper;
import com.dan.commons.dto.huespedes.*;
import com.reserva_hotel.huespedes.entity.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {
    @Override
    public Huesped requestAEntidad(HuespedRequest request) {
        return request != null ?
                Huesped.crear(
                        request.nombre().trim(),
                        request.apellidoPaterno().trim(),
                        request.apellidoMaterno().trim(),
                        request.email().trim(),
                        request.telefono().trim(),
                        request.documento().trim(),
                        request.nacionalidad().trim()) : null;
    }

    @Override
    public HuespedResponse entidadAResponse(Huesped entidad) {
        return entidad != null ?
                new HuespedResponse(
                        entidad.getId(),
                        String.join(" ",
                                entidad.getNombre(),
                                entidad.getApellidoPaterno(),
                                entidad.getApellidoMaterno()),
                        entidad.getEmail(),
                        entidad.getTelefono(),
                        entidad.getDocumento(),
                        entidad.getNacionalidad()) : null;
    }
}
