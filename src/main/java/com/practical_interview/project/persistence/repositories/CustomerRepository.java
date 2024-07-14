package com.practical_interview.project.persistence.repositories;

import com.practical_interview.project.persistence.entities.CustomerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByUserId(String userId);

    @Query("SELECT c FROM CustomerEntity c WHERE " +
            "(:name IS NULL OR :name = '' OR c.firstName LIKE %:name% OR c.lastName LIKE %:name%)")
    List<CustomerEntity> searchByCustomerName(String name, Pageable pageable);
}
