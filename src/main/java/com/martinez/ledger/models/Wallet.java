package com.martinez.ledger.models;

import java.math.BigDecimal;

public class Wallet {
    private BigDecimal balance;

    public Wallet(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }
}
