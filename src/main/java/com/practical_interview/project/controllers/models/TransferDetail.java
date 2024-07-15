package com.practical_interview.project.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TransferDetail {
    private String transferAccountId;

    private String transferAccountName;
}
