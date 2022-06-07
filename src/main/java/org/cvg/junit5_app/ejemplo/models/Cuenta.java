package org.cvg.junit5_app.ejemplo.models;

import lombok.Data;
import org.cvg.junit5_app.ejemplo.exceptions.InsuficientMoneyException;

import java.math.BigDecimal;

@Data
public class Cuenta{
    private Banco banco;
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto){
        BigDecimal saldo = this.saldo.subtract(monto);
        if (saldo.compareTo(BigDecimal.ZERO) < 0) throw new InsuficientMoneyException("Dinero insuficiente");
        this.saldo = saldo;
    }
    public void credito(BigDecimal monto){
        BigDecimal saldo = this.saldo.add(monto);
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Cuenta)) return false;

        Cuenta c = (Cuenta) o;
        if (this.persona == null || this.saldo == null) return false;
        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
