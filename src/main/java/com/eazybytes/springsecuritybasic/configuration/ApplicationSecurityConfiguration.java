package com.eazybytes.springsecuritybasic.configuration;

import com.eazybytes.springsecuritybasic.enumeration.AuthorityEnum;
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
    @Qualifier("inMemoryUserDetailsManagerDemoImpl")
    private UserDetailsManagerDemo userDetailsManagerDemo;

    private final String ADMIN_AUTHORITY = AuthorityEnum.ADMIN.getName();
    private final String READ_AUTHORITY = AuthorityEnum.READ.getName();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/myAccount").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("/customer").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("/news").hasAnyAuthority(ADMIN_AUTHORITY, READ_AUTHORITY)
            .antMatchers("/").permitAll()
        .and()
        .formLogin()
        .and()
        .httpBasic()
        .and()
        .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsManager userDetailsManager = this.userDetailsManagerDemo.createUserDetailsManagerDemo();
        auth.userDetailsService(userDetailsManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
