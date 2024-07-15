package com.practical_interview.project.controllers.dtos;

public record TransferRequest(
        String fromCustomerId,
        String toCustomerId,

        Long transferAmount
) {

}
