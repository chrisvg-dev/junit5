package org.cvg.junit5_app.ejemplo.exceptions;
public class InsuficientMoneyException extends RuntimeException {

    public InsuficientMoneyException(String message) {
        super(message);
    }
}
