package com.frizzer.pioneerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PioneerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PioneerApiApplication.class, args);
    }

}
