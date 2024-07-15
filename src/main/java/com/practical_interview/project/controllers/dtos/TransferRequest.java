package com.practical_interview.project.controllers.dtos;

import java.math.BigDecimal;

public record TransferRequest(
        String fromCustomerId,
        String toCustomerId,

        BigDecimal transferAmount
) {

}
