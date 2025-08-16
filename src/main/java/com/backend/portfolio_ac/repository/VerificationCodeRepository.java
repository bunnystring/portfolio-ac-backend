package com.backend.portfolio_ac.repository;

import com.backend.portfolio_ac.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {

    VerificationCode findByCode(String code);

    VerificationCode findByUser_Email(String email);

}
