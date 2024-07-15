package com.practical_interview.project.persistence.entities;

import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_transaction_id")
    private TransactionEntity relatedTransaction;

    @OneToMany(mappedBy = "relatedTransaction", fetch = FetchType.LAZY)
    private Collection<TransactionEntity> relatedTransactions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id_fk", referencedColumnName = "uuid")
    private AccountEntity account;
}
