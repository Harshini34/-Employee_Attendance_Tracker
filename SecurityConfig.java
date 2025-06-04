package com.example.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/login", "/css/**").permitAll()
                .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/default", true).permitAll()
            .and()
                .logout().permitAll();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var uds = new InMemoryUserDetailsManager();
        uds.createUser(User.withUsername("employee").password("pass").roles("EMPLOYEE").build());
        uds.createUser(User.withUsername("manager").password("pass").roles("MANAGER").build());
        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Not for production
    }
}