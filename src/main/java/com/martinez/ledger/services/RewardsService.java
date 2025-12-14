package com.martinez.ledger.services;

import com.martinez.ledger.events.TransactionCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RewardsService {

    @Async
    @EventListener
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        BigDecimal points = new BigDecimal(10);
        System.out.println("REWARDS:  " + points + " points");
    }
}
