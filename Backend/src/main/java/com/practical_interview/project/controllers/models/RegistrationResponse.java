package com.practical_interview.project.controllers.models;

import com.practical_interview.project.domain.models.CustomerDetail;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private CustomerDetail customerDetail;
}
