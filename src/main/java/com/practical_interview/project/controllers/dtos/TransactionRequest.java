package com.practical_interview.project.controllers.dtos;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;

import java.math.BigDecimal;

public record TransactionRequest(
        TransactionTypeEnum transactionType,
        BigDecimal transactionAmount,

        String userId
) {
}
