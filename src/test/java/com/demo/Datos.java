package com.demo;

import com.demo.models.Banco;
import com.demo.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {
    public static Optional<Cuenta> crearCuenta001() {
        return Optional.of(new Cuenta(1L, "Ryan", new BigDecimal("1000")));
    }

    public static Optional<Cuenta> crearCuenta002() {
        return Optional.of(new Cuenta(2L, "Jhon", new BigDecimal("2000")));
    }

    public static Optional<Banco> crearBanco() {
        return Optional.of(new Banco(1L, "BCP", 0));
    }
}
