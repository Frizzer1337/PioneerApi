package com.devtools.frizzer.pioneerapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table
public class Account {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private BigDecimal balance;
}
