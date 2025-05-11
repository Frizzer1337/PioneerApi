package com.devtools.frizzer.pioneerapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table
public class Customer implements UserDetails {
    @Id
    private long id;
    @Column(name ="name", nullable = false)
    private String username;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer")
    private List<EmailData> emailData;

    @OneToMany(mappedBy = "customer")
    private List<PhoneData> phoneData;

    @OneToOne(mappedBy = "customer")
    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
