package com.eazybytes.springsecuritybasic.configuration;

import com.eazybytes.springsecuritybasic.enumeration.AuthorityEnum;
import com.eazybytes.springsecuritybasic.enumeration.RoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${application.allowCORS.urls}")
    private List<String> allowCORSUrls;

    @Value("${application.allowCORS.methods}")
    private List<String> allowCORSmMethods;

    /**
     * The user detail manager
     * Uncomment Autowired to injection the user details manager
     * Uncomment and set Qualifier to specify the implementation
     *  inMemoryCustomUserDetailsManagerImpl: in memory custom user details manager implementation
     *  inJdbcCustomUserDetailsManagerImpl: in JDBC custom user details manager implementation
     */
//    @Autowired
//    @Qualifier("inMemoryCustomUserDetailsManagerImpl")
//    private UserDetailsManager userDetailsManager;
//
//    // Uncomment if it going to User Details Manager
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.userDetailsManager);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors().configurationSource(createCORSConfiguration())
        .and()
        .csrf()
            .ignoringAntMatchers("/contact")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and().authorizeRequests()
            .antMatchers("/user").hasAuthority(AuthorityEnum.READ.getName())
            .antMatchers("/getAdminInfo").hasRole(RoleEnum.ADMINISTRATOR.getName())
        .and()
        .formLogin()
        .and()
        .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public CorsConfigurationSource createCORSConfiguration() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(allowCORSUrls);
                config.setAllowedMethods(allowCORSmMethods);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                return config;
            }
        };
    }
}
