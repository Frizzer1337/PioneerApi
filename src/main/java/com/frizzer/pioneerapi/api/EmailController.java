package com.frizzer.pioneerapi.api;

import com.frizzer.pioneerapi.domain.dto.EmailDto;
import com.frizzer.pioneerapi.domain.dto.EmailUpdateDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.service.AuthService;
import com.frizzer.pioneerapi.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {this.service = service;}

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid EmailDto data) {
        Customer customer = AuthService.getPrincipal();
        service.save(data.getEmail(), customer.getId());
        return ResponseEntity.ok("Email успешно добавлен");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody @Valid EmailUpdateDto data) {
        Customer customer = AuthService.getPrincipal();
        service.update(data, customer.getId());
        return ResponseEntity.ok("Email успешно обновлен");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody @Valid EmailDto data) {
        Customer customer = AuthService.getPrincipal();
        service.delete(data.getEmail(),customer.getId());
        return ResponseEntity.ok("Email успешно удален");
    }

}
