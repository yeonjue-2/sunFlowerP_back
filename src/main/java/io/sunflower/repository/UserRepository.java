package io.sunflower.repository;

import io.sunflower.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPersonalId(String personalId);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByKakaoId(Long kakaoId);
    Optional<User> findByEmail(String email);
}
