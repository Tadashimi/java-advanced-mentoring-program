package com.epam.homework2.securityconfiguration;

import com.epam.homework2.auth.Homework2ApplicationUserDetailWithBrutForceProtectionService;
import com.epam.homework2.bruteforceprotection.Homework2ApplicationAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Homework2ApplicationSecurityConfigurationWithDBUsersWithPasswordEncoderAndFormBasedAuthentificationAndBruteForceProtection extends WebSecurityConfigurerAdapter {

    @Autowired
    private Homework2ApplicationUserDetailWithBrutForceProtectionService userDetailService;

    @Autowired
    private Homework2ApplicationAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setAuthoritiesMapper(authoritiesMapper());
        return provider;
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setDefaultAuthority("USER");
        return authorityMapper;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .formLogin()
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout_page")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .and()
                .authorizeRequests()
                .antMatchers("/blocked", "/", "/login", "/logout", "/about", "/css/*")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
