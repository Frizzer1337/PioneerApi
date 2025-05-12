package com.frizzer.pioneerapi.dao;

import com.frizzer.pioneerapi.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT * FROM account WHERE customer_id = :customerId FOR UPDATE", nativeQuery = true)
    Optional<Account> findByCustomerIdForUpdate(long customerId);
}
