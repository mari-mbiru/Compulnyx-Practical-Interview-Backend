package com.practical_interview.project.persistence.repositories;

import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("""
      select a from AccountEntity a inner join a.customer c
      where c.userId = :customerId
      """)
    public Optional<AccountEntity> findByCustomerId(String customerId);
}
