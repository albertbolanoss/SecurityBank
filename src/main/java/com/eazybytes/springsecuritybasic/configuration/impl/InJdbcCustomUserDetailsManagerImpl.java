package com.eazybytes.springsecuritybasic.configuration.impl;

import com.eazybytes.springsecuritybasic.model.Customer;
import com.eazybytes.springsecuritybasic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class InJdbcCustomUserDetailsManagerImpl implements UserDetailsManager {
    @Autowired
    private CustomerService customerService;

    @Override
    public void createUser(UserDetails userDetails) {
        Customer customer = new Customer(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities().stream().findFirst().get().getAuthority(),
            userDetails.isEnabled()
        );

        this.customerService.save(customer);
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
        return this.customerService.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = this.customerService.findByEmail(email);
        return new CustomUserDetailsImpl(customer);
    }
}
