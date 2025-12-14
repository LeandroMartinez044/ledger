package com.martinez.ledger.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    private String id;

    private String sourceWalletId;

    private String targetWalletId;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    private Instant timestamp;

    public Transaction() {}

    public Transaction(String id, String sourceWalletId, String targetWalletId, BigDecimal amount) {
        this.id = id;
        this.sourceWalletId = sourceWalletId;
        this.targetWalletId = targetWalletId;
        this.amount = amount;
        this.timestamp = Instant.now();
    }
}
