package com.practical_interview.project.controllers.models;

public record TransferRequest(
        String fromCustomerId,
        String toCustomerId,

        Long transferAmount
) {

}
