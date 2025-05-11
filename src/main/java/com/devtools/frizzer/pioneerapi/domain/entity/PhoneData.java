package com.devtools.frizzer.pioneerapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table
@Getter
public class PhoneData {
    @Id
    @Column
    private long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private String phoneNumber;
}
