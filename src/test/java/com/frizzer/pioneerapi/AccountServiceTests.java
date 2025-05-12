package com.frizzer.pioneerapi;

import com.frizzer.pioneerapi.dao.AccountRepository;
import com.frizzer.pioneerapi.domain.entity.Account;
import com.frizzer.pioneerapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void successfulTransfer() {
        Account from = new Account(1L, null, new BigDecimal("100.00"), null);
        Account to = new Account(2L, null, new BigDecimal("50.00"), null);

        Mockito.when(accountRepository.findByCustomerIdForUpdate(1L)).thenReturn(Optional.of(from));
        Mockito.when(accountRepository.findByCustomerIdForUpdate(2L)).thenReturn(Optional.of(to));

        accountService.transfer(1L, 2L, new BigDecimal("30.00"));

        assertEquals(new BigDecimal("70.00"), from.getBalance());
        assertEquals(new BigDecimal("80.00"), to.getBalance());
    }

    @Test
    void sameUserThrow() {
        assertThrows(ResponseStatusException.class,
                () -> accountService.transfer(1L, 1L, new BigDecimal("10.00")),
                "Нельзя перевести самому себе");
    }


    @Test
    void insufficientFunds() {
        Account from = new Account(1L, null, new BigDecimal("10.00"), null);
        Account to = new Account(2L, null, new BigDecimal("50.00"), null);

        Mockito.when(accountRepository.findByCustomerIdForUpdate(1L)).thenReturn(Optional.of(from));
        Mockito.when(accountRepository.findByCustomerIdForUpdate(2L)).thenReturn(Optional.of(to));

        assertThrows(ResponseStatusException.class,
                () -> accountService.transfer(1L, 2L, new BigDecimal("30.00")),
                "Недостаточно средств");
    }

    @Test
    void senderNotFound() {
        Account from = new Account(1L, null, new BigDecimal("10.00"), null);

        Mockito.when(accountRepository.findByCustomerIdForUpdate(1L)).thenReturn(Optional.of(from));
        Mockito.when(accountRepository.findByCustomerIdForUpdate(2L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> accountService.transfer(1L, 2L, new BigDecimal("30.00")),
                "Отправитель не найден");
    }

    @Test
    void recipientNotFound() {
        Account from = new Account(1L, null, new BigDecimal("10.00"), null);

        Mockito.when(accountRepository.findByCustomerIdForUpdate(1L)).thenReturn(Optional.of(from));
        Mockito.when(accountRepository.findByCustomerIdForUpdate(2L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> accountService.transfer(1L, 2L, new BigDecimal("30.00")),
                "Получатель не найден");
    }

}
