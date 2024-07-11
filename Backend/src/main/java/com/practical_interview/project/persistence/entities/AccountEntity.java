package com.practical_interview.project.persistence.entities;

import java.util.ArrayList;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private UUID Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_internal_id_fk", referencedColumnName = "internal_id")
    public CustomerEntity customer;

    @Column(name = "account_balance")
    @NotBlank
    private Long accountBalance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transactions")
    private ArrayList<TransactionEntity> transactions;
}
