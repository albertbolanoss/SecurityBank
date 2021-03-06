package com.eazybytes.springsecuritybasic.configuration.inmemory.impl;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCustomUserDetailsManagerImpl implements UserDetailsManager, UserDetailsPasswordService {
    protected final Log logger = LogFactory.getLog(getClass());
    private final Map<String, InMemoryCustomUserDetailsImpl> users = new HashMap<>();

    private AuthenticationManager authenticationManager;

    public InMemoryCustomUserDetailsManagerImpl(UserDetails... users) {
        for (UserDetails user : users) {
            createUser(user);
        }
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "User should not exist");
        InMemoryCustomUserDetailsImpl userDetails = new InMemoryCustomUserDetailsImpl(user);

        users.put(user.getUsername().toLowerCase(), userDetails);
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isTrue(userExists(user.getUsername()), "User should exist");
        users.put(user.getUsername().toLowerCase(), new InMemoryCustomUserDetailsImpl(user));
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword)  {
        try {
            Authentication currentUser = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                    .orElseThrow(() -> new AccessDeniedException("Required authentication"));
            String username = currentUser.getName();


            if (authenticationManager != null) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        username, oldPassword));
            } else {
                logger.debug("No Authentication Manager");
            }

            InMemoryCustomUserDetailsImpl user = Optional.ofNullable(users.get(username)).orElseThrow(
                    () -> new IllegalStateException());
            user.setPassword(newPassword);

        } catch (Exception e ) {
            logger.debug("Change password: " + e.getMessage());
        }
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

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        String username = user.getUsername();
        InMemoryCustomUserDetailsImpl mutableUser = users.get(username.toLowerCase());
        mutableUser.setPassword(newPassword);

        return mutableUser;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
