package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.dtos.TransactionRequest;
import com.practical_interview.project.controllers.dtos.TransactionResponse;
import com.practical_interview.project.controllers.dtos.TransferRequest;
import com.practical_interview.project.controllers.dtos.TransferResponse;
import com.practical_interview.project.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.practical_interview.project.config.Constants.BASE_URL;


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
