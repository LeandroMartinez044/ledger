package com.martinez.ledger.config;

import com.martinez.ledger.models.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class LedgerConfig {

    @Bean
    public ConcurrentHashMap<String, Wallet> wallets() {
        ConcurrentHashMap<String, Wallet> wallets = new ConcurrentHashMap<>();

        String walletNumberUser1 = "walletNumberA";
        String walletNumberUser2 = "walletNUmberB";
        String walletNumberUser3 = "walletNUmberC";

        // initial supply 100.0
        wallets.put(walletNumberUser1, new Wallet(new BigDecimal(50)));
        wallets.put(walletNumberUser2, new Wallet(new BigDecimal(30)));
        wallets.put(walletNumberUser3, new Wallet(new BigDecimal(20)));

        return wallets;
    }
}
