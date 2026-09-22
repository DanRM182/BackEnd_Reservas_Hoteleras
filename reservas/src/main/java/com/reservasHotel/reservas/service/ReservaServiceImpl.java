package com.reservasHotel.reservas.service;

import com.reservasHotel.reservas.dto.ReservaRequest;
import com.reservasHotel.reservas.dto.ReservaResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservaServiceImpl implements ReservaService {
    @Override
    public List<ReservaResponse> listar() {
        return List.of();
    }

    @Override
    public ReservaResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public ReservaResponse registrar(ReservaRequest request) {
        return null;
    }

    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }
}
