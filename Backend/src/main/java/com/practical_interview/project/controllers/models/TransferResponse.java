package com.practical_interview.project.controllers.models;

import lombok.Builder;

@Builder
public class TransferResponse {
    AccountBalanceResponse fromAccountDetail;
    AccountBalanceResponse toAccountDetail;
}
