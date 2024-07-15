package com.practical_interview.project.controllers.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponse {

    private BigDecimal accountBalance;
}
