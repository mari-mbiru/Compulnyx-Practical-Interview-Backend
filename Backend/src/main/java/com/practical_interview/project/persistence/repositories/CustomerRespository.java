package com.practical_interview.project.persistence.repositories;

import com.practical_interview.project.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRespository extends JpaRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByUserID(String email);

}
