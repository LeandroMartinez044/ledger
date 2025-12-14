package com.martinez.ledger.controllers;

import com.martinez.ledger.services.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    @Autowired
    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }


    @PostMapping("/transfer")
    public ResponseEntity initiateTransfer(@RequestBody TransferRequest request) {

        try {
            ledgerService.transfer(
                    request.getWalletNumberSource(),
                    request.getWalletNumberTarget(),
                    request.getAmount()
            );
            return ResponseEntity.ok("transaction complete");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
