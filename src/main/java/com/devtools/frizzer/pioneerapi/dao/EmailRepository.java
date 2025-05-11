package com.devtools.frizzer.pioneerapi.dao;

import com.devtools.frizzer.pioneerapi.domain.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findByEmail(String email);
}
