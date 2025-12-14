package com.martinez.ledger.controllers;

import com.sun.istack.NotNull;

import java.math.BigDecimal;

public class TransferRequest {

    @NotNull
    private String walletNumberSource;

    @NotNull
    private String walletNumberTarget;

    @NotNull
    private BigDecimal amount;

    public TransferRequest() {}

    public String getWalletNumberSource() {
        return walletNumberSource;
    }

    public String getWalletNumberTarget() {
        return walletNumberTarget;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
