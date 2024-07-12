package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.models.AccountBalanceResponse;
import com.practical_interview.project.controllers.models.Transaction;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.practical_interview.project.config.ConfigConstants.BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL + "/customers")
public class CustomerController {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/{userId}/balance")
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(
            @PathVariable("userId") String userId
    ) {
        var account = accountRepository.findByCustomerId(userId).orElseThrow();
        return ResponseEntity.ok(new AccountBalanceResponse(userId, account.getUuid().toString(), account.getAccountBalance()));
    }


    @GetMapping("/{userId}/mini-statement")
    public ResponseEntity<Collection<Transaction>> getMiniStatement(
            @PathVariable("userId") String userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        var account = accountRepository.findByCustomerId(userId).orElseThrow();

        Pageable pageable = PageRequest.of(0, limit, Sort.by("dateCreated").descending());
        var transactions = transactionRepository.findAllByAccount(account, pageable);

        return ResponseEntity.ok(transactions.stream()
                .map(transactionEntity ->
                        Transaction.builder()
                                .uuid(transactionEntity.getUuid().toString())
                                .dateCreated(transactionEntity.getDateCreated())
                                .transactionType(transactionEntity.getTransactionType())
                                .transactionAmount(transactionEntity.getTransactionAmount())
                                .build())
                .collect(Collectors.toCollection(ArrayList::new)));
    }
}
