package com.eazybytes.config.inmemory;

import com.eazybytes.config.UserDetailsManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserDetailsManagerImpl implements UserDetailsManagerFactory {
    @Value( "${default.admin.password}" )
    private String defaultAdminPassword;

    @Value( "${default.user.password}" )
    private String defaultUserPassword;

    @Value( "${default.admin.username}" )
    private String defaultAdminUsername;

    @Value( "${default.user.username}" )
    private String defaultUserUsername;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDetailsManager create() {
        CustomInMemoryUserDetailsManager userDetailsService = new CustomInMemoryUserDetailsManager();
        String adminPassword = passwordEncoder.encode(this.defaultAdminPassword);
        String userPassword = passwordEncoder.encode(this.defaultUserPassword);

        UserDetails admin = User.withUsername(this.defaultAdminUsername)
                .password(adminPassword)
                .authorities("admin").build();

        UserDetails user = User.withUsername(this.defaultUserUsername)
                .password(userPassword)
                .authorities("read").build();

        userDetailsService.createUser(admin);
        userDetailsService.createUser(user);

        return userDetailsService;
    }
}
