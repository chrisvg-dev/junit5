package org.cvg.junit5_app.ejemplo.models;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Se genera una sola instancia para la ejecución de las pruebas
//@TestInstance(TestInstance.Lifecycle.PER_METHOD) // Se genera uns instancia por cada método test, es el que está por defecto
class BancoTest {
    Cuenta cuenta, cuenta2;

    /**
     * SE EJECUTA ANTES DE QUE SE GENERE LA INSTANCIA DE LA CLASE TEST
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando la clase test, pero no la instancia, este método va primero");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Se ejecuta despues de todos los test para cerrar algún recurso por ejemplo");
    }

    @BeforeEach
    void init() {
        System.out.println("Init");
        cuenta = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando metodo de prueba");
    }

    /**
     * REPEATED TESTS
     */
    @DisplayName("Test para validar transferencias entre cuentas")
    @RepeatedTest(value = 5, name = "{displayName} -> {currentRepetition} de {totalRepetitions}")
    void transferirDineroCuentasRepetir(RepetitionInfo repetition) {

        if (repetition.getCurrentRepetition() == 2) {
            System.out.println("SIUUUUUU");
        }

        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.transferir(cuenta2, cuenta, new BigDecimal(500));
        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()),
                () -> assertEquals("3000", cuenta.getSaldo().toPlainString())
        );
    }

    @Nested
    @DisplayName("Nested class for bank test")
    class BackTest {
        @DisplayName("Test para validar transferencias entre cuentas")
        @Test
        void transferirDineroCuentas() {
            Banco banco = new Banco();
            banco.setNombre("BBVA");
            banco.transferir(cuenta2, cuenta, new BigDecimal(500));
            assertAll(
                    () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()),
                    () -> assertEquals("3000", cuenta.getSaldo().toPlainString())
            );
        }

        @DisplayName("Test para validar relacion banco - cuenta")
        //@Disabled
        @Test
        void testRelacionBancoCuentas() {
            //fail();
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
}