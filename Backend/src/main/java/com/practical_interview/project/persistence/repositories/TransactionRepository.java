package com.practical_interview.project.persistence.repositories;

import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByAccount(AccountEntity account, Pageable pageable);
}
