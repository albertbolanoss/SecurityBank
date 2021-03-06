package com.eazybytes.springsecuritybasic.configuration.inmemory.impl;

import com.eazybytes.springsecuritybasic.configuration.UserDetailsManagerDemo;
import com.eazybytes.springsecuritybasic.enumeration.AuthorityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class InMemoryUserDetailsManagerDemoImpl implements UserDetailsManagerDemo {
    @Value( "${default.admin.username}" )
    private String defaultAdminUsername;

    @Value( "${default.admin.password}" )
    private String defaultAdminPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsManager createUserDetailsManagerDemo() {
        InMemoryCustomUserDetailsManagerImpl userDetailsService = new InMemoryCustomUserDetailsManagerImpl();
        String adminPassword = passwordEncoder.encode(this.defaultAdminPassword);

        UserDetails admin = User.withUsername(this.defaultAdminUsername)
                .password(adminPassword)
                .authorities(AuthorityEnum.ADMIN.getName()).build();

        userDetailsService.createUser(admin);

        return userDetailsService;
    }
}
