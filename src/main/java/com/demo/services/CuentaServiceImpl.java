package com.demo.services;

import com.demo.models.Banco;
import com.demo.models.Cuenta;
import com.demo.repositories.BancoRepository;
import com.demo.repositories.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {
    private final BancoRepository bancoRepository;
    private final CuentaRepository cuentaRepository;

    @Override
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        return bancoRepository.findById(bancoId).getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSalgo(Long cuentaId) {
        return cuentaRepository.findById(cuentaId).getSaldo();
    }

    @Override
    public void transferir(Long bancoId, Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto) {
        Banco banco = bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.update(banco);
        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRepository.update(cuentaOrigen);
        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRepository.update(cuentaDestino);

    }
}
