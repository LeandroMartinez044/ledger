package com.martinez.ledger.events;

import java.math.BigDecimal;

public class TransactionCompletedEvent {

    private final String transactionId;
    private final String sourceWallet;
    private final String targetWallet;
    private final BigDecimal amount;

    public TransactionCompletedEvent(String transactionId, String sourceWallet, String targetWallet, BigDecimal amount) {
        this.transactionId = transactionId;
        this.sourceWallet = sourceWallet;
        this.targetWallet = targetWallet;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getSourceWallet() {
        return sourceWallet;
    }

    public String getTargetWallet() {
        return targetWallet;
    }
}
