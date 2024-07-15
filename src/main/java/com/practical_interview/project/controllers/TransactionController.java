package com.practical_interview.project.controllers;

import com.practical_interview.project.controllers.models.Transaction;
import com.practical_interview.project.controllers.models.TransferDetail;
import com.practical_interview.project.exceptions.AppException;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
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
        var transaction = transactionRepository.findById(UUID.fromString(transactionId)).orElseThrow(() -> new AppException("Transaction not found", HttpStatus.NOT_FOUND));

        var transactionDetail = Transaction.builder()
                .transactionType(transaction.getTransactionType())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionType(transaction.getTransactionType())
                .dateCreated(transaction.getDateCreated())
                .transferId(String.valueOf(transaction.getTransferId()))
                .build();

        if (Objects.nonNull(transaction.getRelatedTransaction())) {
            transactionDetail.setTransferDetail(
                    new TransferDetail(
                            String.valueOf(transaction.getAccount().getUuid()),
                            transaction.getAccount().getCustomer().getCustomerName()));
        }

        return ResponseEntity.ok(transactionDetail);
    }
}
