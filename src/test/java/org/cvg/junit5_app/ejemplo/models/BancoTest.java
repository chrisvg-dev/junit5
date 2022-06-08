package org.cvg.junit5_app.ejemplo.models;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BancoTest {

    @DisplayName("Test para validar transferencias entre cuentas")
    @Test
    void transferirDineroCuentas() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.transferir(cuenta2, cuenta, new BigDecimal(500));
        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()),
                () -> assertEquals("3000", cuenta.getSaldo().toPlainString())
        );
    }

    @DisplayName("Test para validar relacion banco - cuenta")
    @Disabled
    @Test
    void testRelacionBancoCuentas() {
        fail();
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);
        banco.setNombre("BBVA");

        banco.transferir(cuenta2, cuenta, new BigDecimal(500));

        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "El valor recibido no es igual al esperado..."),
                () -> assertEquals("3000", cuenta.getSaldo().toPlainString(), () -> "El valor recibido no es igual al esperado..."),

                () -> assertEquals(2, banco.getCuentas().size(), () -> "La cantidad de cuentas es menor a la esperada"),
                () -> assertEquals("BBVA", cuenta.getBanco().getNombre(), () -> "El nombre del banco no coincide"),

                () -> assertEquals("Andres", banco.getCuentas().stream()
                        .filter( x -> x.getPersona().equals("Andres") )
                        .findFirst()
                        .get().getPersona(), () -> "No existen clientes con el nombre Andres"),
                () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch( x -> x.getPersona().equals("Andres") ), () -> "No existen clientes con el nombre Andres")
        );
    }
}