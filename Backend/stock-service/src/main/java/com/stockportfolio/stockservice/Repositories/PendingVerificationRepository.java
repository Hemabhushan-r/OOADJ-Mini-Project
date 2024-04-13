package com.stockportfolio.stockservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockportfolio.stockservice.Models.PendingUser;

import java.util.Optional;

@Repository
public interface PendingVerificationRepository extends JpaRepository<PendingUser, Long> {
    Optional<PendingUser> findByEmail(String email);

    Optional<PendingUser> findByUsername(String username);

    Optional<PendingUser> findByPanNumber(String panNumber);

    Optional<PendingUser> findByPhoneNumber(String phoneNumber);
}
