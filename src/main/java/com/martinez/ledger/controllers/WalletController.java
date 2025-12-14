package com.martinez.ledger.controllers;

import com.martinez.ledger.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final ConcurrentHashMap<String, Wallet> wallets;

    @Autowired
    public WalletController(ConcurrentHashMap<String, Wallet> wallets) {
        this.wallets = wallets;
    }

    @GetMapping
    public ConcurrentHashMap<String, Wallet> getWallet() {
        return wallets;
    }
}
