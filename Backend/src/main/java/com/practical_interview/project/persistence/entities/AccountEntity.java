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
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue
    @Column(name = "uuid")
    private UUID uuid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_uuid_fk", referencedColumnName = "uuid")
    public CustomerEntity customer;

    @NotNull
    private Long accountBalance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private ArrayList<TransactionEntity> transactions;
}
