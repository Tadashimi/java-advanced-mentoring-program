package com.epam.homework2.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

//@Service
public class Homework2ApplicationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    public Homework2ApplicationUserDetailService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.userRepository = userRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Cannot find user bu username - " + s);
        }
        List<AuthGroup> authGroupList = authGroupRepository.findByUsername(s);
        return new Homework2ApplicationUserPrincipal(user, authGroupList);
    }
}
