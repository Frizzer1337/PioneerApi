package com.frizzer.pioneerapi.dao;

import com.frizzer.pioneerapi.domain.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findByEmail(String email);

    Optional<EmailData> findByEmailAndCustomer_Id(String email, Long customerId);

    boolean existsByEmail(String email);
}
