package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.models.TransactionRequest;
import com.practical_interview.project.controllers.models.TransactionResponse;
import com.practical_interview.project.controllers.models.TransferRequest;
import com.practical_interview.project.controllers.models.TransferResponse;
import com.practical_interview.project.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
