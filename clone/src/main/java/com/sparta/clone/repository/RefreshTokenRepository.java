package com.sparta.clone.repository;

import com.sparta.clone.domain.RefreshToken;
import com.sparta.clone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByUser(User user);
}
