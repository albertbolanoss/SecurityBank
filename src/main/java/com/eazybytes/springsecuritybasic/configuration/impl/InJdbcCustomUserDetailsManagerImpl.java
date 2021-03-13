package com.eazybytes.springsecuritybasic.configuration.impl;

import com.eazybytes.springsecuritybasic.model.Customer;
import com.eazybytes.springsecuritybasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

@Configuration
public class InJdbcCustomUserDetailsManagerImpl implements UserDetailsManager {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createUser(UserDetails userDetails) {
        Customer customer = new Customer(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities().stream().findFirst().get().getAuthority(),
            userDetails.isEnabled()
        );

        customerRepository.save(customer);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String email) {
        return this.customerRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<Customer> customers = this.customerRepository.findByEmail(email);
        Customer customer = null;

        if (customers != null && !customers.isEmpty()) {
            customer = customers.stream().findFirst().get();
        } else {
            throw new UsernameNotFoundException(String.format("the email (%s) does not exist", email));
        }

        return new CustomUserDetailsImpl(customer);
    }
}
