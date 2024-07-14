package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.models.AccountBalanceResponse;
import com.practical_interview.project.controllers.models.Customer;
import com.practical_interview.project.controllers.models.Transaction;
import com.practical_interview.project.exceptions.AppException;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.CustomerRepository;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.practical_interview.project.config.ConfigConstants.BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL + "/customers")
public class CustomerController {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/{userId}/balance")
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(
            @PathVariable("userId") String userId
    ) {
        var account = accountRepository.findByCustomerId(userId).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(new AccountBalanceResponse(userId, account.getUuid().toString(), account.getAccountBalance()));
    }


    @GetMapping("/{userId}/mini-statement")
    public ResponseEntity<List<Transaction>> getMiniStatement(
            @PathVariable("userId") String userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        var account = accountRepository.findByCustomerId(userId).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

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

    @GetMapping()
    public ResponseEntity<List<Customer>> getCustomers(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "customerName", required = false) String customerName) {

        var customers = customerRepository.searchByCustomerName(customerName,
                PageRequest.of(0, limit, Sort.by("firstName").descending()));

        return ResponseEntity.ok(customers.stream()
                .map(customer -> Customer.builder()
                        .customerId(customer.getUserId())
                        .customerName(customer.getCustomerName())
                        .build())
                .collect(Collectors.toList()));
    }
}
