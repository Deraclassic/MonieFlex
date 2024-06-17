package com.Java020.MonieFlex.repository;

import com.Java020.MonieFlex.domain.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository  extends JpaRepository<ConfirmationToken, Long>{
    Optional<ConfirmationToken> findByToken(String token);
}
