package com.practical_interview.project.controllers.dtos;


public record RegisterRequest(

        String firstName,

        String lastName,

        String email,
        String customerId
) {
}
