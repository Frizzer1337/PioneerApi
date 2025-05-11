package com.devtools.frizzer.pioneerapi.api;

import com.devtools.frizzer.pioneerapi.domain.entity.EmailData;
import com.devtools.frizzer.pioneerapi.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {this.service = service;}

    @PostMapping("/")
    public ResponseEntity<EmailData> save(@RequestBody EmailData data) {
        return ResponseEntity.ok(service.save(data));
    }

}
