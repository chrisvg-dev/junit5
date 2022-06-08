package org.cvg.junit5_app.ejemplo.models;

import org.cvg.junit5_app.ejemplo.exceptions.InsuficientMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    public void init() {
        System.out.println("Iniciando método test");
        this.cuenta = new Cuenta("CRISTHIAN VILLEGAS", new BigDecimal("1000.12345"));
    }

    @DisplayName("Test para validar el nombre del propietario de la cuenta")
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
    @DisplayName("Test para validar si las cuentas son iguales")
    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2, () -> "Las cuentas no son iguales");
    }

    @DisplayName("Test para validar la funcion debito de la cuenta")
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

    @DisplayName("Test para validar la funcion credito de la cuenta")
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


    @DisplayName("Test para devolver una excepcion en caso de dinero insuficiente...")
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

    /**
     * VALIDATION TEST
     */
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {

    }
    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloLinux() {

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void noWindows() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void enableOnJava15() {
    }

    @Test
    void imprimirSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach( (key, val) -> System.out.println(key + ": " + val) );
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "1.8.0_111")
    void testJavaVersion() {
    }

    @DisplayName("Ejecutar solo en entornos de 64 bits")
    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void disableOn32Bits() {
    }

    @DisplayName("Ejecutar solo en entornos de 32 bits")
    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void disableOn64Bits() {
    }

    @DisplayName("Ejecutar solo en sesiones de ciertos usuarios")
    @Test
    @EnabledIfSystemProperty(named = "user.name", matches = "crist")
    void testUsername() {
    }

    @Test
    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    void testDev() {
    }

    @Test
    void imprimirVariablesAmbiente() {
        Map<String, String> map = System.getenv();
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-17.0.2")
    void testJavaHome() {
    }

    @Test
    @DisplayName("Test para pc con 12 nucleos de procesamiento")
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "12")
    void testProcessors() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
    void testEnv() {
    }
    @Test
    @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
    void testDisabledEnv() {
    }
}