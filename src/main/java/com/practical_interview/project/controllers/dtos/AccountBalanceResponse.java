package com.practical_interview.project.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponse {

    private String userId;

    private String accountId;

    private BigDecimal accountBalance;
}
