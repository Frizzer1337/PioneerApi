package com.devtools.frizzer.pioneerapi.api;

import com.devtools.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.devtools.frizzer.pioneerapi.domain.dto.CustomerLoginDto;
import com.devtools.frizzer.pioneerapi.domain.dto.LoginResponseDto;
import com.devtools.frizzer.pioneerapi.domain.entity.Customer;
import com.devtools.frizzer.pioneerapi.service.AuthService;
import com.devtools.frizzer.pioneerapi.service.CustomerService;
import com.devtools.frizzer.pioneerapi.service.JwtService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final CustomerService customerService;

    public CustomerController(AuthService authService, JwtService jwtService, CustomerService customerService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.customerService = customerService;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomerDto>> filter(
            @RequestParam(value = "dateOfBirth", required = false) LocalDate date,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            Pageable pageable) {
        return ResponseEntity.ok(customerService.filter(date, name, phone, email, pageable));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody CustomerLoginDto dto) {
        Customer customer = authService.auth(dto);
        String token = jwtService.generateToken(customer);
        return ResponseEntity.ok(LoginResponseDto.builder()
                                                 .token(token)
                                                 .expires(jwtService.getExpirationTime())
                                                 .build());
    }
}
