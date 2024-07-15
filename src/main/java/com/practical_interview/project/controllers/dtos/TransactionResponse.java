package com.practical_interview.project.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {

    private Long accountBalance;
}
