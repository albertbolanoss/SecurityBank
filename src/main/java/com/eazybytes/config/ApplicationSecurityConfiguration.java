package com.eazybytes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value( "${default.password}" )
    private String defaultPassword;

    @Value( "${default.admin.username}" )
    private String defaultAdminUsername;

    @Value( "${default.user.username}" )
    private String defaultUserUsername;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/myAccount").hasAuthority("admin")
            .antMatchers("/myBalance").hasAuthority("admin")
            .antMatchers("myCards").hasAuthority("admin")
            .antMatchers("/notices").hasAnyAuthority("admin", "read")
            .antMatchers("/contact").hasAnyAuthority("admin", "read")
        .and()
        .formLogin()
        .and()
        .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        CustomInMemoryUserDetailsManager userDetailsService = new CustomInMemoryUserDetailsManager();

        UserDetails admin = User.withUsername(this.defaultAdminUsername)
                .password(this.defaultPassword).authorities("admin").build();

        UserDetails user = User.withUsername(this.defaultUserUsername)
                .password(this.defaultPassword).authorities("read").build();

        userDetailsService.createUser(admin);
        userDetailsService.createUser(user);
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
