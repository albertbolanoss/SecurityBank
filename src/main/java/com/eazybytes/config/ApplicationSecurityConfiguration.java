package com.eazybytes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("inMemoryUserDetailsManagerImpl")
    private UserDetailsManagerFactory userDetailsManagerFactory;

    private static final String ADMIN_AUTHORITY = "admin";
    private static final String READ_AUTHORITY = "read";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/myAccount").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("/myBalance").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("myCards").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("/notices").hasAnyAuthority(ADMIN_AUTHORITY, READ_AUTHORITY)
            .antMatchers("/contact").hasAnyAuthority(ADMIN_AUTHORITY, READ_AUTHORITY)
        .and()
        .formLogin()
        .and()
        .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsManager userDetailsManager = this.userDetailsManagerFactory.create();
        auth.userDetailsService(userDetailsManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
