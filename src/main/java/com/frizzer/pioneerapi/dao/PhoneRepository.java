package com.frizzer.pioneerapi.dao;

import com.frizzer.pioneerapi.domain.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneData, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<PhoneData> findByPhoneNumberAndCustomer_Id(String phoneNumber, long customerId);
}
