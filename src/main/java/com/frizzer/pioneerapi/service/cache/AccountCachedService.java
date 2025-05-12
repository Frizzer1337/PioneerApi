package com.frizzer.pioneerapi.service.cache;

import com.frizzer.pioneerapi.service.AccountService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.frizzer.pioneerapi.service.cache.CacheNames.CUSTOMERS;

@Service
public class AccountCachedService {

    private final AccountService accountService;

    public AccountCachedService(AccountService accountService) {this.accountService = accountService;}

    @CacheEvict(value = CUSTOMERS, allEntries = true)
    public BigDecimal transfer(long senderId, long recipientId, BigDecimal amount) {
        return accountService.transfer(senderId, recipientId, amount);
    }
}
