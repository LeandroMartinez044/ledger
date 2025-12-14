package com.martinez.ledger.services;

import com.martinez.ledger.events.TransactionCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudDetectionService {

    @Async
    @EventListener
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        if (event.getAmount().compareTo(new BigDecimal(0.10)) < 1) {
            System.out.println("✅Event: transaction_id" + event.getTransactionId() + ", possible fraud");
        }
    }
}
