package com.reservasHotel.auth.services;

import java.util.Set;

import com.reservasHotel.auth.dto.UsuarioRequest;
import com.reservasHotel.auth.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}
