package com.eazybytes.springsecuritybasic.configuration.impl;

import com.eazybytes.springsecuritybasic.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetailsImpl implements UserDetails, UserDetailsPasswordService {
    private final Customer customer;

    public CustomUserDetailsImpl(Customer customer) {
        this.customer = customer;
    }

    public CustomUserDetailsImpl(UserDetails userDetails) {
        this.customer = new Customer();
        this.customer.setEmail(userDetails.getUsername());
        this.customer.setPwd(userDetails.getPassword());
        this.customer.setEnabled(userDetails.isEnabled());
        // this.customer.setPermissions();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // List<GrantedAuthority> authorities = new ArrayList<>();
        List<GrantedAuthority> authorities = customer.getPermissions().stream()
                .map(e -> new SimpleGrantedAuthority(e.getAuthority().getId()))
                .collect(Collectors.toList());
        // authorities.add(new SimpleGrantedAuthority(""));
        // authorities.add(new SimpleGrantedAuthority(""));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.customer.getPwd();
    }

    @Override
    public String getUsername() {
        return this.customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.customer.getEnabled();
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String s) {
        return null;
    }
}
