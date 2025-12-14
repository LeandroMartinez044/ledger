package com.martinez.ledger.services;

import com.martinez.ledger.events.TransactionCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    @EventListener
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        System.out.println("✅Event: transaction_id" + event.getTransactionId());
    }
}
