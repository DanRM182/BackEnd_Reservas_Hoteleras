package com.reservasHotel.huespedes.mapper;

import com.reservasHotel.commons.dto.huespedes.HuespedRequest;
import com.reservasHotel.commons.dto.huespedes.HuespedResponse;
import com.reservasHotel.commons.mapper.CommonMapper;
import com.reservasHotel.huespedes.entity.Huesped;
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
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno(),
                        entidad.getEmail(),
                        entidad.getTelefono(),
                        entidad.getDocumento(),
                        entidad.getNacionalidad()) : null;
    }
}
