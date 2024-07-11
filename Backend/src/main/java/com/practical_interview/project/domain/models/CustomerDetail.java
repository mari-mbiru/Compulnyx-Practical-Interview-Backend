package com.practical_interview.project.domain.models;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDetail {

    private String userID;

    private String userName;
}
