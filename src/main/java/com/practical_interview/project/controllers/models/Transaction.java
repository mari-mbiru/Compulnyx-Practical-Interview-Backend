package com.practical_interview.project.controllers.models;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class Transaction {
    private String uuid;

    private LocalDateTime dateCreated;

    private Long transactionAmount;

    private TransactionTypeEnum transactionType;

    private String transferId;
}
