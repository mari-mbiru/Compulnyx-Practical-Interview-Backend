package com.practical_interview.project.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountBalanceResponse {

    private String userId;

    private String accountId;

    private Long accountBalance;
}
