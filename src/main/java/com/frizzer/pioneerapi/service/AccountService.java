package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.dao.AccountRepository;
import com.frizzer.pioneerapi.domain.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private static final BigDecimal MAX_PERCENT = BigDecimal.valueOf(2.07);
    private static final BigDecimal ADDED_PERCENT = BigDecimal.valueOf(1.1);

    public AccountService(AccountRepository accountRepository) {this.accountRepository = accountRepository;}

    @Scheduled(cron = "*/30 * * * * *")
    public void upBalance() {
        log.info("Started updating account balance");
        List<Account> accounts = accountRepository.findAll().stream().map(account -> {
            var balance = account.getBalance();
            var startBalance = account.getStartingBalance();
            if (checkBalance(balance, startBalance)) {
                account.setBalance(balance.multiply(ADDED_PERCENT));
                return account;
            }
            return null;
        }).filter(Objects::nonNull).toList();
        accountRepository.saveAll(accounts);
        log.info("Finished updating account balance, updated {} accounts", accounts.size());
    }

    private boolean checkBalance(BigDecimal balance, BigDecimal startBalance) {
        return balance.multiply(ADDED_PERCENT).compareTo(startBalance.multiply(MAX_PERCENT)) < 0;
    }

}
