package com.martinez.ledger.services;

import com.martinez.ledger.events.TransactionCompletedEvent;
import com.martinez.ledger.models.Transaction;
import com.martinez.ledger.models.Wallet;
import com.martinez.ledger.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LedgerService {

    private final ConcurrentHashMap<String, Wallet> wallets;
    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public LedgerService(ConcurrentHashMap<String, Wallet> wallets,
                         TransactionRepository transactionRepository,
                         ApplicationEventPublisher eventPublisher) {
        this.wallets = wallets;
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void transfer(String walletNumberSource, String walletNumberTarget, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("amount must be greater than 0");
        }

        Wallet source = wallets.get(walletNumberSource);
        if (source == null) {
            throw new RuntimeException("source wallet doesn't exist");
        }

        Wallet target = wallets.get(walletNumberTarget);
        if (target == null) {
            throw new RuntimeException("target wallet doesn't exist");
        }

        Wallet lockSource, lockTarget;

        if (walletNumberSource.compareTo(walletNumberTarget) > 0) {
            lockSource = target;
            lockTarget = source;
        } else {
            lockSource = source;
            lockTarget = target;
        }

        synchronized (lockSource) {
            synchronized (lockTarget) {

                if (source.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("source wallet doesn't have the enough amount");
                }

                BigDecimal newSourceBalance = source.getBalance().subtract(amount);
                source.setBalance(newSourceBalance);

                BigDecimal newTargetBalance = target.getBalance().add(amount);
                target.setBalance(newTargetBalance);

                String transactionId =  UUID.randomUUID().toString();
                Transaction transaction = new Transaction(transactionId, walletNumberSource, walletNumberTarget, amount);

                try {
                    transactionRepository.save(transaction);
                } catch (Exception e) {
                    source.setBalance(source.getBalance().add(amount));
                    target.setBalance(target.getBalance().subtract(amount));
                    throw new RuntimeException("insertion has failed");
                }

                TransactionCompletedEvent event = new TransactionCompletedEvent(transactionId, walletNumberSource, walletNumberTarget, amount);
                eventPublisher.publishEvent(event);
            }
        }
    }
}