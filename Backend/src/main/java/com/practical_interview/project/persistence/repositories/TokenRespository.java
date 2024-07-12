package com.practical_interview.project.persistence.repositories;

import com.practical_interview.project.persistence.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRespository extends JpaRepository<TokenEntity, UUID> {

    @Query("""
      select t from TokenEntity t inner join t.customer u
      where u.uuid = :customerUuid and (t.expired = false or t.revoked = false)
      """)
    List<TokenEntity> findAllValidTokenByUser(UUID customerUuid);

    Optional<TokenEntity> findByToken(String token);
}
