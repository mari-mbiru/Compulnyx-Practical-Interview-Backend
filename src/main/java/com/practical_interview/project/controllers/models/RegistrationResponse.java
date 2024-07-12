package com.practical_interview.project.controllers.models;

import com.practical_interview.project.domain.models.CustomerDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private CustomerDetail customerDetail;
}
