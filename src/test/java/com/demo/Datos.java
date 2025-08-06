package com.demo;

import com.demo.models.Banco;
import com.demo.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
    public static Cuenta crearCuenta001() {
        return new Cuenta(1L, "Ryan", new BigDecimal("1000"));
    }
    public static Cuenta crearCuenta002() {
        return new Cuenta(2L, "Jhon", new BigDecimal("2000"));
    }

    public static Banco crearBanco() {
        return new Banco(1L, "BCP", 0);
    }
}
