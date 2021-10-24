package com.epam.homework2.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class Homework2ApplicationUserPrincipal implements UserDetails {

    private User user;
    /*
    To store auth group in separate table
    */
    private List<AuthGroup> authGroups;

    public Homework2ApplicationUserPrincipal(User user, List<AuthGroup> authGroups) {
        this.user = user;
        this.authGroups = authGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isNull(authGroups)) {
            return Collections.emptyList();
        }
        return authGroups.stream()
                .map(group -> new SimpleGrantedAuthority(group.getAuthGroup()))
                .collect(Collectors.toSet());
    }

    /*
    User role
     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(this.user.getUser_role()));
//    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
