package com.practical_interview.project.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDetail {

    private String userID;

    private String userName;

    private String customerPin;

}
