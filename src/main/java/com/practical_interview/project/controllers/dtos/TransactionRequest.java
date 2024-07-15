package com.practical_interview.project.controllers.dtos;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;

public record TransactionRequest(
        TransactionTypeEnum transactionType,
        Long transactionAmount,

        String userId
) {
}
