package com.eazybytes.springsecuritybasic.configuration.impl;

import com.eazybytes.springsecuritybasic.model.Authority;
import com.eazybytes.springsecuritybasic.model.Customer;
import com.eazybytes.springsecuritybasic.model.Permission;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class InMemoryCustomUserDetailsManagerImpl implements UserDetailsManager {
    protected final Log logger = LogFactory.getLog(getClass());

    private final Map<String, CustomUserDetailsImpl> users = new HashMap<>();

    @Autowired
    public InMemoryCustomUserDetailsManagerImpl(@Value("${application.admin.username}") String adminUsername,
                                                @Value("${application.admin.password}") String  adminPassword,
                                                @Value("${application.admin.authorities}") String[] authorities) {
        Customer customer = new Customer();
        customer.setEmail(adminUsername);
        customer.setPwd(adminPassword);
        customer.setEnabled(true);

        List<Permission> permissions = Arrays.asList(authorities).stream()
                .map(authority ->  new Permission(null, new Authority(authority, true), null))
                .collect(Collectors.toList());

        customer.setPermissions(permissions);

        createUser(new CustomUserDetailsImpl(customer));
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "User should not exist");
        users.put(user.getUsername().toLowerCase(), new CustomUserDetailsImpl(user));
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isTrue(userExists(user.getUsername()), "User should exist");
        users.put(user.getUsername().toLowerCase(), new CustomUserDetailsImpl(user));
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword)  {
       // pending for implement
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = Optional.ofNullable(users.get(username)).orElseThrow(
                () -> new UsernameNotFoundException("No found user"));

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), user.getAuthorities());
    }
}
