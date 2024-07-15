package com.practical_interview.project.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue
    private UUID uuid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_uuid_fk", referencedColumnName = "uuid")
    public CustomerEntity customer;

    @NotNull
    private BigDecimal accountBalance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Collection<TransactionEntity> transactions;
}
