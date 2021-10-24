package com.epam.homework2.securityconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;

/*
Uncomment strings below to work with InMemory stored users
 */
//@Configuration
//@EnableWebSecurity
public class Homework2ApplicationSecurityConfigurationWithInMemoryUsers extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/about", "/css/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /*
    userDetailsService with inMemory stored users
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin@email.com")
                .password("admin0")
                .roles("ADMIN")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user@email.com")
                .password("password0")
                .roles("USER")
                .build();
        List<UserDetails> users = Arrays.asList(admin, user);
        return new InMemoryUserDetailsManager(users);
    }
}
