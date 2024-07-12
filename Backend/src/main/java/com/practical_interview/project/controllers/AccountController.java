package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.models.*;
import com.practical_interview.project.domain.services.AccountService;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.CustomerRepository;
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
@RequestMapping(BASE_URL + "/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody TransactionRequest request
            ) {
        return ResponseEntity.ok(accountService.makeTransaction(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> createTransfer(
            @RequestBody TransferRequest request
    ) {
        return ResponseEntity.ok(accountService.makeTransfer(request));
    }

}
