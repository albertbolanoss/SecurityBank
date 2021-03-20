package com.eazybytes.springsecuritybasic.service;

import com.eazybytes.springsecuritybasic.model.Customer;

public interface CustomerService {
    void save(Customer customer);

    boolean existsByEmail(String email);

    Customer findByEmail (String email);
}
