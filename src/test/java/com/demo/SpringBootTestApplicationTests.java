package com.demo;

import com.demo.exceptions.DineroInsuficienteException;
import com.demo.models.Banco;
import com.demo.models.Cuenta;
import com.demo.repositories.BancoRepository;
import com.demo.repositories.CuentaRepository;
import com.demo.services.CuentaServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.demo.Datos.*;

@SpringBootTest(classes = com.demo.SpringBootTestApplication.class)
class SpringBootTestApplicationTests {

    @Mock
    CuentaRepository cuentaRepository;
    @Mock
    BancoRepository bancoRepository;

    @InjectMocks
    CuentaServiceImpl cuentaService;



    @Test
    void contextLoads() {
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco());

        BigDecimal saldoOrigen = cuentaService.revisarSalgo(1L);
        BigDecimal saldoDestino = cuentaService.revisarSalgo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());
        cuentaService.transferir(1L, 1L, 2L, new BigDecimal("100"));

        saldoOrigen = cuentaService.revisarSalgo(1L);
        saldoDestino = cuentaService.revisarSalgo(2L);

        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());
        int total = cuentaService.revisarTotalTransferencias(1L);

        assertEquals(1, total);

        // se ejecuta 3 veces
        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);

        verify(cuentaRepository, times(2)).update(any(Cuenta.class));
        verify(bancoRepository, times(2)).findById(1L);
        verify(bancoRepository).update(any(Banco.class));
        verify(cuentaRepository, times(6)).findById(anyLong());
        verify(cuentaRepository, never()).findAll();
    }

    @Test
    void contextLoads2() {
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco());

        BigDecimal saldoOrigen = cuentaService.revisarSalgo(1L);
        BigDecimal saldoDestino = cuentaService.revisarSalgo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        assertThrows(DineroInsuficienteException.class, () -> {
            cuentaService.transferir(1L, 1L, 2L, new BigDecimal("1200"));
        });

        saldoOrigen = cuentaService.revisarSalgo(1L);
        saldoDestino = cuentaService.revisarSalgo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());
        int total = cuentaService.revisarTotalTransferencias(1L);

        assertEquals(1, total);

        // se ejecuta 3 veces
        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        //never() -> verifica que nunca se ejecute
        verify(cuentaRepository, never()).update(any(Cuenta.class));
        verify(bancoRepository, times(2)).findById(1L);
        verify(cuentaRepository, times(5)).findById(anyLong());
        verify(cuentaRepository, never()).findAll();
        verify(bancoRepository).update(any(Banco.class));

    }


    @Test
    void contextLoad3() {
        Cuenta cuenta1 = cuentaService.findById(1L);
        Cuenta cuenta2 = cuentaService.findById(1L);
        //Verifica que sea el mismo objeto
//        System.out.println(cuenta1);
        assertSame(cuenta1, cuenta2);
//        assertEquals("Ryan", cuenta1.getPersona());
        verify(cuentaRepository, times(2)).findById(1L);

    }

}
