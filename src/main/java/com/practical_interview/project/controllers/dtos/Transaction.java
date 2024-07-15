package com.practical_interview.project.controllers.dtos;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class Transaction {
    private String uuid;

    private LocalDateTime dateCreated;

    private BigDecimal transactionAmount;

    private TransactionTypeEnum transactionType;

    private String transferId;

    private TransferDetail transferDetail;
}
