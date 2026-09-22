package com.reservasHotel.commons.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje) { }

