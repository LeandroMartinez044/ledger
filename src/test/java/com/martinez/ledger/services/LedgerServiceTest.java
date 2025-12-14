package com.martinez.ledger.services;

import com.martinez.ledger.events.TransactionCompletedEvent;
import com.martinez.ledger.models.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ExtendWith(MockitoExtension.class)
public class LedgerServiceTest {

    private LedgerService sut;

    private ConcurrentHashMap<String, Wallet> wallets;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    String walletNumberUser1;
    String walletNumberUser2;
    String walletNumberUser3;

    @BeforeEach
    void setup() {
        wallets = new ConcurrentHashMap<>();

        walletNumberUser1 = UUID.randomUUID().toString();
        walletNumberUser2 = UUID.randomUUID().toString();
        walletNumberUser3 = UUID.randomUUID().toString();

        wallets.put(walletNumberUser1, new Wallet(new BigDecimal(50)));
        wallets.put(walletNumberUser2, new Wallet(new BigDecimal(30)));
        wallets.put(walletNumberUser3, new Wallet(new BigDecimal(20)));

        sut = new LedgerService(wallets, eventPublisher);
    }

    @Test
    public void transfer() {
        BigDecimal amountToTransfer = new BigDecimal(30);
        sut.transfer(walletNumberUser1, walletNumberUser2, amountToTransfer);

        assertEquals(new BigDecimal(20.00), wallets.get(walletNumberUser1).getBalance());
        assertEquals(new BigDecimal(60.00), wallets.get(walletNumberUser2).getBalance());
        assertEquals(new BigDecimal(20.00), wallets.get(walletNumberUser3).getBalance());

        verify(eventPublisher, times(1)).publishEvent(Mockito.any(TransactionCompletedEvent.class));
    }

    @Test
    public void transferWhenSourceWalletDoesNotHaveTheEnoughAmount() {
        BigDecimal amountToTransfer = new BigDecimal(55);

        assertThrows(RuntimeException.class, () -> {
            sut.transfer(walletNumberUser1, walletNumberUser2, amountToTransfer);
        });
        verify(eventPublisher, times(0)).publishEvent(Mockito.any(TransactionCompletedEvent.class));
    }

    @Test
    public void transferWhenAmountIsLessThan1() {
        BigDecimal amountToTransfer = new BigDecimal(0);

        assertThrows(RuntimeException.class, () -> {
            sut.transfer(walletNumberUser1, walletNumberUser2, amountToTransfer);
        });
        verify(eventPublisher, times(0)).publishEvent(any());
    }

    @Test
    public void transferWhenSourceWalletDoesNotExits() {
        BigDecimal amountToTransfer = new BigDecimal(55);

        assertThrows(RuntimeException.class, () -> {
            sut.transfer("111", walletNumberUser2, amountToTransfer);
        });
        verify(eventPublisher, times(0)).publishEvent(any());
    }

    @Test
    public void transferWhenTargetWalletDoesNotExits() {
        BigDecimal amountToTransfer = new BigDecimal(55);

        assertThrows(RuntimeException.class, () -> {
            sut.transfer(walletNumberUser1, "sdad", amountToTransfer);
        });
        verify(eventPublisher, times(0)).publishEvent(any());
    }
}