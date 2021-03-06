package com.eazybytes.springsecuritybasic.repository;

import com.eazybytes.springsecuritybasic.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query("SELECT email FROM Customer c WHERE c.email = :email")
    String findByEmail(String email);
}
