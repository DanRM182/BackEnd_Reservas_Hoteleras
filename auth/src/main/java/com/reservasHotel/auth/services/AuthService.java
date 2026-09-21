package com.reservasHotel.auth.services;

import com.reservasHotel.auth.dto.LoginRequest;
import com.reservasHotel.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}
