package org.cvg.junit5_app.ejemplo.models;

import org.cvg.junit5_app.ejemplo.exceptions.InsuficientMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    public void init() {
        this.cuenta = new Cuenta("CRISTHIAN VILLEGAS", new BigDecimal("1000.12345"));
    }

    @DisplayName("Test para el nombre del propietario de la cuenta")
    @Test
    void testNombreCuenta() {
        //cuenta.setPersona("CRISTHIAN VILLEGAS");
        String valorEsperado = "CRISTHIAN VILLEGAS";
        String valorReal = this.cuenta.getPersona();
        assertAll(
                () -> assertNotNull(valorReal, () -> "El valor no puede ser nulo"),
                () -> assertEquals(valorEsperado, valorReal, () -> "El valor esperado no es igual al valor real"),
                () -> assertTrue(valorReal.equals(valorEsperado), () -> "Los valores son diferentes...")
        );
    }

    @DisplayName("Test para comprobar el saldo de la cuenta")
    @Test
    void testSaldoCuenta() {
        assertAll(
                () -> assertEquals(1000.12345, this.cuenta.getSaldo().doubleValue(), () -> "El saldo no es igual al esperado"),
                () -> assertFalse(cuenta.getSaldo().compareTo( BigDecimal.ZERO ) < 0, () -> "El saldo no debe ser menor a 0"),
                () -> assertTrue(cuenta.getSaldo().compareTo( BigDecimal.ZERO ) > 0, () -> "El saldo debe ser mayor a 0"),
                () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo no debe ser nulo")
        );
    }

    /**
     * TDD
     */
    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2, () -> "Las cuentas no son iguales");
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("CRISTHIAN", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertAll(
                () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo no debe ser nulo"),
                () -> assertEquals(900, cuenta.getSaldo().intValue(), "El valor esperado no es igual al recibido"),
                () -> assertEquals("900.12345", cuenta.getSaldo().toPlainString(), () -> "El valor esperado no es igual al recibido")
        );
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("CRISTHIAN", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertAll(
                () -> assertNotNull(cuenta.getSaldo(), () -> "El saldo no debe ser null"),
                () -> assertEquals(1100, cuenta.getSaldo().intValue(), () -> "El valor recibido es diferente al esperado"),
                () -> assertEquals("1100.12345", cuenta.getSaldo().toPlainString(), () -> "El valor recibido es diferente al esperado")
        );
    }

    @Test
    void testDineroInsuficienteException() {
        Cuenta cuenta = new Cuenta("CRISTHIAN", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(InsuficientMoneyException.class, () -> {
            cuenta.debito( new BigDecimal(1500) );
        });
        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }
}