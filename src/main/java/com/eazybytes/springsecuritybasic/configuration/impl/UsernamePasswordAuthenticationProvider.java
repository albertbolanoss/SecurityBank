package com.eazybytes.springsecuritybasic.configuration.impl;

import com.eazybytes.springsecuritybasic.model.Customer;
import com.eazybytes.springsecuritybasic.model.Permission;
import com.eazybytes.springsecuritybasic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Customer customer = customerService.findByEmail(username);

        if (customer != null) {
            if (passwordEncoder.matches(password, customer.getPwd())) {
                List<GrantedAuthority> authorities = buildAuthorities(customer.getPermissions());
                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<GrantedAuthority> buildAuthorities(List<Permission> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Optional.ofNullable(permissions).orElseThrow(() ->new BadCredentialsException("Haven't permissions"));

        for (Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getAuthority().getId()));
        }

        return authorities;
    }
}
