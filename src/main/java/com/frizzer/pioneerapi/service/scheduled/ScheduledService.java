package com.frizzer.pioneerapi.service.scheduled;

import com.frizzer.pioneerapi.dao.AccountRepository;
import com.frizzer.pioneerapi.domain.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.frizzer.pioneerapi.service.cache.CacheNames.CUSTOMERS;

@Service
@Slf4j
public class ScheduledService {

    private final AccountRepository accountRepository;
    private static final BigDecimal MAX_PERCENT = BigDecimal.valueOf(2.07);
    private static final BigDecimal ADDED_PERCENT = BigDecimal.valueOf(1.1);
    private final CacheManager cacheManager;

    public ScheduledService(AccountRepository accountRepository, CacheManager cacheManager) {
        this.accountRepository = accountRepository;
        this.cacheManager = cacheManager;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void upBalance() {
        log.info("Начато обновление баланса аккаунтов");
        List<Account> accounts = accountRepository.findAll().stream().map(account -> {
            var balance = account.getBalance();
            var startBalance = account.getStartingBalance();
            if (checkBalance(balance, startBalance)) {
                account.setBalance(balance.multiply(ADDED_PERCENT));
                return account;
            }
            return null;
        }).filter(Objects::nonNull).toList();
        if (!accounts.isEmpty()) {
            accountRepository.saveAll(accounts);
            cacheManager.getCache(CUSTOMERS).clear();
        }
        log.info("Обновление балансов аккаунтов завершено, баланс изменен на {} аккаунтах", accounts.size());
    }

    private boolean checkBalance(BigDecimal balance, BigDecimal startBalance) {
        return balance.multiply(ADDED_PERCENT).compareTo(startBalance.multiply(MAX_PERCENT)) < 0;
    }

}
