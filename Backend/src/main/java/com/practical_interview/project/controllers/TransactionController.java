package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.models.Transaction;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.practical_interview.project.config.ConfigConstants.BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL + "/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getAccountBalance(
            @PathVariable("transactionId") String transactionId
    ) {
        var transaction = transactionRepository.findById(UUID.fromString(transactionId)).orElseThrow();
        return ResponseEntity.ok(new Transaction(
                transaction.getUuid().toString(),
                transaction.getDateCreated(),
                transaction.getTransactionAmount(),
                transaction.getTransactionType(),
                transaction.getTransferId().toString()));
    }
}
