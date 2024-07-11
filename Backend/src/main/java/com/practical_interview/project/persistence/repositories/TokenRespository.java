package com.practical_interview.project.persistence.repositories;

import com.practical_interview.project.persistence.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRespository extends JpaRepository<TokenEntity, UUID> {

    @Query(value = """
      select t from TokenEntity t inner join CustomerEntity u\s
      on t.customer.Id = u.Id\s
      where u.Id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenEntity> findAllValidTokenByUser(UUID id);

    Optional<TokenEntity> findByToken(String token);
}
