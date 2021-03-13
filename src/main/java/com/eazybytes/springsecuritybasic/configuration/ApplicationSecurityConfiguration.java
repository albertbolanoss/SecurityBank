package com.eazybytes.springsecuritybasic.configuration;

import com.eazybytes.springsecuritybasic.configuration.impl.CustomUserDetailsImpl;
import com.eazybytes.springsecuritybasic.enumeration.AuthorityEnum;
import com.eazybytes.springsecuritybasic.model.Customer;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    @Qualifier("inMemoryCustomUserDetailsManagerImpl")
//    private UserDetailsManager userDetailsManager;

    @Autowired
    @Qualifier("inJdbcCustomUserDetailsManagerImpl")
    private UserDetailsManager userDetailsManager;

    @Value( "${default.admin.username}" )
    private String defaultAdminUsername;

    @Value( "${default.admin.password}" )
    private String defaultAdminPassword;

    private final String ADMIN_AUTHORITY = AuthorityEnum.ADMIN.getName();
    private final String READ_AUTHORITY = AuthorityEnum.READ.getName();

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                return config;
            }
        })
        .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and().authorizeRequests()
            .antMatchers("/myAccount").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("/customer").hasAuthority(ADMIN_AUTHORITY)
            .antMatchers("/news").hasAnyAuthority(ADMIN_AUTHORITY, READ_AUTHORITY)
            .antMatchers("/").permitAll()
        .and()
        .formLogin()
        .and()
        .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Customer customer = new Customer();
        customer.setEmail(this.defaultAdminUsername);
        customer.setPwd(this.defaultAdminPassword);
        customer.setRole(ADMIN_AUTHORITY);
        customer.setEnabled(true);

        this.userDetailsManager.createUser(new CustomUserDetailsImpl(customer));
        auth.userDetailsService(this.userDetailsManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
