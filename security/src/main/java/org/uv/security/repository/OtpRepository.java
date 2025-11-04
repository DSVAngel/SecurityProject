package org.uv.security.repository;
import org.uv.security.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmailAndCodeAndIsUsedFalseAndExpirationTimeAfter(
            String email,
            String code,
            LocalDateTime currentTime
    );

    void deleteByEmail(String email);
    void deleteByExpirationTimeBefore(LocalDateTime time);
}