package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.dao.AccountRepository;
import com.frizzer.pioneerapi.domain.entity.Account;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {this.accountRepository = accountRepository;}

    @Transactional
    public BigDecimal transfer(long senderId, long recipientId, BigDecimal amount) {
        if (senderId == recipientId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя перевести самому себе");
        }

        try {

            log.info("Начат перевод денег от пользователя {} к пользователю {} сумма перевода {}",
                    senderId,
                    recipientId,
                    amount);

            Account sender = accountRepository.findByCustomerIdForUpdate(senderId)
                                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                      "Отправитель не найден"));

            Account recipient = accountRepository.findByCustomerIdForUpdate(recipientId)
                                                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                         "Получатель не найден"));

            if (sender.getBalance().compareTo(amount) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно средств");
            }

            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount));

            accountRepository.save(sender);
            accountRepository.save(recipient);

            log.info("Закончен перевод денег от пользователя {} к пользователю {} сумма перевода {}",
                    senderId,
                    recipientId,
                    amount);
            return sender.getBalance();
        } catch (ResponseStatusException e) {
            log.error("Перевод от пользователя {} к пользователю {} сумма перевода {} отменен в связи с {}",
                    senderId,
                    recipientId,
                    amount,
                    e.getMessage());
            throw e;
        }
    }

}
