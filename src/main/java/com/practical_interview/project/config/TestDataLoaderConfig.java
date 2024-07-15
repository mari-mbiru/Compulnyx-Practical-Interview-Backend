package com.practical_interview.project.config;

import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.CustomerEntity;
import com.practical_interview.project.persistence.entities.TransactionEntity;
import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.CustomerRepository;
import com.practical_interview.project.persistence.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
public class TestDataLoaderConfig {

    @Bean
    public CommandLineRunner loadData(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            CustomerEntity firstCustomer = CustomerEntity.builder()
                    .firstName("Joyce")
                    .lastName("Akinyi")
                    .email("joyce.Akinyi@example.com")
                    .userId("75210938")
                    .userPin(passwordEncoder.encode("12345"))
                    .build();
            customerRepository.save(firstCustomer);

            var account = AccountEntity.builder()
                    .accountBalance(0L)
                    .customer(firstCustomer)
                    .build();
            accountRepository.save(account);

            CustomerEntity nextCustomer = CustomerEntity.builder()
                    .firstName("Brian")
                    .lastName("Kimani")
                    .email("brian.kimani@example.com")
                    .userId("93516284")
                    .userPin(passwordEncoder.encode("12345"))
                    .build();
            customerRepository.save(nextCustomer);

            var account2 = AccountEntity.builder()
                    .accountBalance(0L)
                    .customer(nextCustomer)
                    .build();
            accountRepository.save(account2);

            var date = LocalDateTime.now();

            var transactions = Arrays.asList(
                    //Deposit account 1
                    TransactionEntity.builder()
                            .transactionAmount(10000L)
                            .transactionType(TransactionTypeEnum.CREDIT)
                            .dateCreated(date)
                            .account(account).build(),

                    //Deposit account 2
                    TransactionEntity.builder()
                            .transactionAmount(10000L)
                            .transactionType(TransactionTypeEnum.CREDIT)
                            .dateCreated(date)
                            .account(account2).build(),

                    //Withdraw account 1
                    TransactionEntity.builder()
                            .transactionAmount(2500L)
                            .transactionType(TransactionTypeEnum.DEBIT)
                            .dateCreated(date)
                            .account(account).build(),

                    //Withdraw account 2
                    TransactionEntity.builder()
                            .transactionAmount(7500L)
                            .transactionType(TransactionTypeEnum.DEBIT)
                            .dateCreated(date)
                            .account(account2).build()


            );

            transactionRepository.saveAll(transactions);

            var transferId = UUID.randomUUID();
            var transferId2 = UUID.randomUUID();

            //Transfer account 2 to account 1
            var transferDebit = TransactionEntity.builder()
                    .transactionAmount(200L)
                    .transactionType(TransactionTypeEnum.CREDIT)
                    .transferId(transferId)
                    .dateCreated(date)
                    .account(account).build();

            var transferCredit = TransactionEntity.builder()
                    .transactionAmount(200L)
                    .transactionType(TransactionTypeEnum.DEBIT)
                    .transferId(transferId)
                    .dateCreated(date)
                    .account(account2).build();

            transferDebit.setRelatedTransaction(transferCredit);
            transferCredit.setRelatedTransaction(transferDebit);

            //Transfer account 1 to account 2
            var transfer2Debit = TransactionEntity.builder()
                    .transactionAmount(1000L)
                    .transactionType(TransactionTypeEnum.DEBIT)
                    .transferId(transferId2)
                    .dateCreated(date)
                    .account(account).build();

            var transfer2Credit = TransactionEntity.builder()
                    .transactionAmount(1000L)
                    .transactionType(TransactionTypeEnum.CREDIT)
                    .transferId(transferId2)
                    .dateCreated(date)
                    .account(account2).build();
            transfer2Debit.setRelatedTransaction(transferCredit);
            transfer2Credit.setRelatedTransaction(transferDebit);

            transactionRepository.saveAll(Arrays.asList(transferCredit, transferDebit, transfer2Credit, transfer2Debit));
        };
    }
}
