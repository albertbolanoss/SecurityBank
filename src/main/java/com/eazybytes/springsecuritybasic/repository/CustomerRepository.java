package com.eazybytes.springsecuritybasic.repository;

import com.eazybytes.springsecuritybasic.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.email = :email")
    boolean existsByEmail(String email);

    List<Customer> findByEmail(String email);
}
