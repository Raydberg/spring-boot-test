package com.demo;

import com.demo.models.Cuenta;
import com.demo.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//Habilita todo el contexto de persistencia JPA
@DataJpaTest
public class IntegracionJpaTest {

    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Ryan", cuenta.orElseThrow().getPersona());
    }

    @Test
    void findByPersona() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Ryan");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertTrue(cuenta.isPresent());
        assertEquals("Ryan", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void testSave() {
        //Given
        Cuenta pepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        // When
//        Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();
        Cuenta save = cuentaRepository.save(pepe);
        Cuenta cuenta = cuentaRepository.findById(save.getId()).orElseThrow();
        //Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
//        assertEquals(3, cuenta.getId());
    }

    @Test
    void testUpdate() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        // When
        Cuenta cuenta = cuentaRepository.save(cuentaPepe);
        //Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

        //When
        cuenta.setSaldo(new BigDecimal("3800"));
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
        //Then
        assertEquals("Pepe", cuentaActualizada.getPersona());
        assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());

    }

    @Test
    void testDelete() {
        //Give
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
        //When
        assertEquals("Jhon", cuenta.getPersona());
        cuentaRepository.delete(cuenta);
        assertThrows(NoSuchElementException.class, () -> cuentaRepository.findByPersona("Jhon").orElseThrow());
        assertEquals(1, cuentaRepository.findAll().size());

    }
}
