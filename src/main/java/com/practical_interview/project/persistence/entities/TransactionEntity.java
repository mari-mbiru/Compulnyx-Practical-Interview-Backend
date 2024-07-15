package com.practical_interview.project.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practical_interview.project.persistence.entities.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue
    private UUID uuid;

    @NotNull
    private LocalDateTime dateCreated;

    @NotNull
    @Min(0)
    private BigDecimal transactionAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    private UUID transferId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id_fk", referencedColumnName = "uuid")
    private AccountEntity account;

    //This is a self-referential relationship. Relating any transactions that are part of a transfer to each other

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_transaction_id")
    @JsonBackReference
    private TransactionEntity relatedTransaction;

    @OneToMany(mappedBy = "relatedTransaction", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<TransactionEntity> relatedTransactions = new HashSet<>();

    public void addRelatedTransaction(TransactionEntity relatedTransaction) {
        relatedTransaction.setRelatedTransaction(this);
        this.getRelatedTransactions().add(relatedTransaction);
    }
}
