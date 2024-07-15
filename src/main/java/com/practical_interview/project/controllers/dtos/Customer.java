package com.practical_interview.project.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Customer {
    private String customerId;
    private String customerName;
}
