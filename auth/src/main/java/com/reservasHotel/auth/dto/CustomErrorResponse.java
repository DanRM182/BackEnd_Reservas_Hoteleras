package com.reservasHotel.auth.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) { }
