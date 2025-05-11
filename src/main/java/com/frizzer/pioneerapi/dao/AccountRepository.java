package com.frizzer.pioneerapi.dao;

import com.frizzer.pioneerapi.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}
