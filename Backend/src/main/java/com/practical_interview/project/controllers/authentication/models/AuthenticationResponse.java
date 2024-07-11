package com.practical_interview.project.controllers.authentication.models;

import com.practical_interview.project.domain.models.CustomerDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private CustomerDetail customerDetail;
}
