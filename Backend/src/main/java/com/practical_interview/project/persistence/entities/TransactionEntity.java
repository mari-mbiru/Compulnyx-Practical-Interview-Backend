package com.practical_interview.project.persistence.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue
    @Column(name = "uuid")
    private UUID uuid;

    @NotNull
    private LocalDateTime dateCreated;

    @NotNull
    @Min(0)
    private Long transactionAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    private UUID transferId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id_fk", referencedColumnName = "uuid")
    private AccountEntity account;
}
