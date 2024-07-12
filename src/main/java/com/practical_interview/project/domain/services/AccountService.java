package com.practical_interview.project.domain.services;

import com.practical_interview.project.controllers.models.*;
import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.TransactionEntity;
import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponse makeTransaction(TransactionRequest request){

        var account = accountRepository.findByCustomerId(request.userId()).orElseThrow();

        var newBalance = getNewBalance(account.getAccountBalance(), request.transactionAmount(), request.transactionType());

        var transaction = TransactionEntity.builder()
                .transactionAmount(request.transactionAmount())
                .transactionType(request.transactionType())
                .account(account)
                .dateCreated(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        account.setAccountBalance(newBalance);
        accountRepository.save(account);

        return TransactionResponse.builder()
                .accountBalance(account.getAccountBalance())
                .build();
    }


    @Transactional
    public TransferResponse makeTransfer(TransferRequest request){

        var fromAccount = accountRepository.findByCustomerId(request.fromCustomerId()).orElseThrow();
        var toAccount = accountRepository.findByCustomerId(request.fromCustomerId()).orElseThrow();

        var fromNewBalance = getNewBalance(fromAccount.getAccountBalance(), request.transferAmount(), TransactionTypeEnum.DEBIT);
        var toNewBalance = getNewBalance(toAccount.getAccountBalance(), request.transferAmount(), TransactionTypeEnum.CREDIT);

        var transferID = UUID.randomUUID();

        var fromTransaction = TransactionEntity.builder()
                .transactionAmount(request.transferAmount())
                .transactionType(TransactionTypeEnum.DEBIT)
                .account(fromAccount)
                .dateCreated(LocalDateTime.now())
                .transferId(transferID)
                .build();
        var toTransaction = TransactionEntity.builder()
                .transactionAmount(request.transferAmount())
                .transactionType(TransactionTypeEnum.CREDIT)
                .account(toAccount)
                .dateCreated(LocalDateTime.now())
                .transferId(transferID)
                .build();;

        transactionRepository.save(fromTransaction);
        transactionRepository.save(toTransaction);


        fromAccount.setAccountBalance(fromNewBalance);
        toAccount.setAccountBalance(toNewBalance);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);



        return TransferResponse.builder()
                .fromAccountDetail(getAccountBalanceResponse(fromAccount))
                .toAccountDetail(getAccountBalanceResponse(toAccount))
                .build();
    }

    private AccountBalanceResponse getAccountBalanceResponse(AccountEntity account){
        return AccountBalanceResponse.builder()
                .accountId(account.getCustomer().getUserId())
                .accountBalance(account.getAccountBalance())
                .accountId(account.getUuid().toString())
                .build();
    }
    private Long getNewBalance(Long accountBalance, Long transactionAmount, TransactionTypeEnum transactionType) {

        if(transactionType== TransactionTypeEnum.CREDIT){
            return accountBalance + transactionAmount;
        } else {
            return accountBalance - transactionAmount;
        }
    }
}
