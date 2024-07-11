package com.practical_interview.project.controllers.authentication.models;


public record RegisterRequest(

        String firstName,

        String lastName,

        String email,
        String customerId
) {
}
