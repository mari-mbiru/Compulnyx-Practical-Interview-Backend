package com.practical_interview.project.controllers.models;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;

public record TransactionRequest(
        TransactionTypeEnum transactionType,
        Long transactionAmount,

        String userId
) {
}
