package com.practical_interview.project.services;

import com.practical_interview.project.controllers.dtos.*;
import com.practical_interview.project.exceptions.AppException;
import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.TransactionEntity;
import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        var account = findAccountByCustomerId(request.userId());

        var newBalance = getNewBalance(account.getAccountBalance(), request.transactionAmount(), request.transactionType());

        var transaction = TransactionEntity.builder()
                .transactionAmount(request.transactionAmount())
                .transactionType(request.transactionType())
                .account(account)
                .dateCreated(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        updateAccountBalance(account, newBalance);

        return TransactionResponse.builder()
                .accountBalance(account.getAccountBalance())
                .build();
    }

    @Transactional
    public TransferResponse makeTransfer(TransferRequest request) {
        // Find sender and receiver accounts
        AccountEntity fromAccount = findAccountByCustomerId(request.fromCustomerId());
        AccountEntity toAccount = findAccountByCustomerId(request.toCustomerId());

        // Calculate new balances
        Long fromNewBalance = getNewBalance(fromAccount.getAccountBalance(), request.transferAmount(), TransactionTypeEnum.DEBIT);
        Long toNewBalance = getNewBalance(toAccount.getAccountBalance(), request.transferAmount(), TransactionTypeEnum.CREDIT);

        // Generate a unique transfer ID
        UUID transferID = UUID.randomUUID();

        // Create debit and credit transactions
        TransactionEntity debitTransaction = createTransaction(request.transferAmount(), TransactionTypeEnum.DEBIT, fromAccount, transferID);
        TransactionEntity creditTransaction = createTransaction(request.transferAmount(), TransactionTypeEnum.CREDIT, toAccount, transferID);

        // Link debit and credit transactions
        debitTransaction.addRelatedTransaction(creditTransaction);
        creditTransaction.addRelatedTransaction(debitTransaction);

        // Save transactions
        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        // Update account balances
        updateAccountBalance(fromAccount, fromNewBalance);
        updateAccountBalance(toAccount, toNewBalance);

        // Return transfer response
        return TransferResponse.builder()
                .fromAccountDetail(getAccountBalanceResponse(fromAccount))
                .toAccountDetail(getAccountBalanceResponse(toAccount))
                .build();
    }

    private TransactionEntity createTransaction(Long transactionAmount, TransactionTypeEnum transactionType, AccountEntity account, UUID transferID) {
        return TransactionEntity.builder()
                .transactionAmount(transactionAmount)
                .transactionType(transactionType)
                .account(account)
                .dateCreated(LocalDateTime.now())
                .transferId(transferID)
                .build();
    }

    private AccountEntity findAccountByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new AppException("User or account does not exist, please try again with another user.", HttpStatus.NOT_FOUND));
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

    private void updateAccountBalance(AccountEntity account, Long newBalance) {
        account.setAccountBalance(newBalance);
        accountRepository.save(account);
    }
}
