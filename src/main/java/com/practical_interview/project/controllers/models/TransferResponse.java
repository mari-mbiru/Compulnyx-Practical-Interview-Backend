package com.practical_interview.project.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    AccountBalanceResponse fromAccountDetail;
    AccountBalanceResponse toAccountDetail;
}
