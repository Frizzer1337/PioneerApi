package com.frizzer.pioneerapi.api;

import com.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.frizzer.pioneerapi.domain.dto.CustomerLoginDto;
import com.frizzer.pioneerapi.domain.dto.LoginResponseDto;
import com.frizzer.pioneerapi.domain.dto.TransferDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.service.AuthService;
import com.frizzer.pioneerapi.service.JwtService;
import com.frizzer.pioneerapi.service.cache.AccountCachedService;
import com.frizzer.pioneerapi.service.cache.CustomerCachedService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final CustomerCachedService service;
    private final AccountCachedService accountService;

    public CustomerController(AuthService authService,
                              JwtService jwtService,
                              CustomerCachedService service,
                              AccountCachedService accountService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomerDto>> filter(
            @RequestParam(value = "dateOfBirth", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy")
            LocalDate date,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            Pageable pageable) {
        return ResponseEntity.ok(service.filter(date, name, phone, email, pageable));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferDto transferDto) {
        Customer customer = AuthService.getPrincipal();
        BigDecimal remainder = accountService.transfer(customer.getId(), transferDto.getId(), transferDto.getAmount());
        return ResponseEntity.ok("Трансфер успешно совершен, на вашем счету осталось: " + remainder);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid CustomerLoginDto dto) {
        Customer customer = authService.auth(dto);
        String token = jwtService.generateToken(customer);
        return ResponseEntity.ok(LoginResponseDto.builder()
                                                 .token(token)
                                                 .expires(jwtService.getExpirationTime())
                                                 .build());
    }
}
