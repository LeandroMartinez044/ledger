package com.martinez.ledger.services;

import com.martinez.ledger.events.TransactionCompletedEvent;
import com.martinez.ledger.models.Transaction;
import com.martinez.ledger.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public NotificationService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Async
    @EventListener
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        Transaction transaction = new Transaction(event.getTransactionId(), event.getSourceWallet(),
                event.getTargetWallet(), event.getAmount());

        transactionRepository.save(transaction);
        System.out.println("✅Event: transaction_id" + event.getTransactionId());
    }
}
