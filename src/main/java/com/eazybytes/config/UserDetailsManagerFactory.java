package com.eazybytes.config;

import org.springframework.security.provisioning.UserDetailsManager;

public interface UserDetailsManagerFactory {
    UserDetailsManager create();
}
