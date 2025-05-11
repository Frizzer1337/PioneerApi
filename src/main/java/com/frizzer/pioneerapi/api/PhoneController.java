package com.frizzer.pioneerapi.api;

import com.frizzer.pioneerapi.domain.dto.PhoneDto;
import com.frizzer.pioneerapi.domain.dto.PhoneUpdateDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.service.AuthService;
import com.frizzer.pioneerapi.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    private final PhoneService service;

    public PhoneController(PhoneService service) {this.service = service;}

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid PhoneDto data) {
        Customer customer = AuthService.getPrincipal();
        service.save(data.getPhone(),customer.getId());
        return ResponseEntity.ok("Номер успешно добавлен");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody @Valid PhoneUpdateDto data) {
        Customer customer = AuthService.getPrincipal();
        service.update(data, customer.getId());
        return ResponseEntity.ok("Номер успешно обновлен");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody @Valid PhoneDto data) {
        Customer customer = AuthService.getPrincipal();
        service.delete(data.getPhone(),customer.getId());
        return ResponseEntity.ok("Номер успешно удален");
    }
}
