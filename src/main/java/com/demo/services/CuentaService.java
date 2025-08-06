package com.demo.services;

import com.demo.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {
    Cuenta findById(Long id);

    int revisarTotalTransferencias(Long bancoId);

    BigDecimal revisarSalgo(Long cuentaId);

    void transferir(Long bancoId, Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto);

}
