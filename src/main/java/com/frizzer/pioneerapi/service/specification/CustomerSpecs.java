package com.frizzer.pioneerapi.service.specification;

import com.frizzer.pioneerapi.domain.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CustomerSpecs {

    public static Specification<Customer> wasBornAfter(LocalDate bornDate) {
        return (root, query, builder) -> checkNull(builder.greaterThan(root.get("dateOfBirth"), bornDate),
                builder,
                bornDate);
    }

    public static Specification<Customer> nameLike(String name) {
        return (root, query, builder) -> checkNull(builder.like(root.get("name"), name + "%"), builder, name);
    }

    public static Specification<Customer> phoneEquals(String phone) {
        return (root, query, builder) -> checkNull(builder.equal(root.get("phoneData").get("phoneNumber"), phone),
                builder,
                phone);
    }

    public static Specification<Customer> emailEquals(String email) {
        return (root, query, builder) -> checkNull(builder.equal(root.get("emailData").get("email"), email),
                builder,
                email);
    }

    private static Predicate checkNull(Predicate predicate, CriteriaBuilder builder, Object param) {
        return param != null ? predicate : builder.conjunction();
    }

}
