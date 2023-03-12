package io.sunflower.user.repository;

import io.sunflower.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByKakaoId(String kakaoId);

    boolean existsByEmailId(String emailId);
    boolean existsByNickname(String nickname);
}
