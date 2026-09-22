package com.reservasHotel.commons.utils;

import com.reservasHotel.commons.exceptions.EntidadRelacionadaException;
import feign.FeignException;

import java.util.function.Consumer;

public class FunctionUtils {
    public static void validarEstadoCitas(Long id, Consumer<Long> validarCitas, String mensaje) {
        try {
            validarCitas.accept(id);
        } catch (FeignException.Conflict e) {
            throw new EntidadRelacionadaException(mensaje);
        }
    }
}
