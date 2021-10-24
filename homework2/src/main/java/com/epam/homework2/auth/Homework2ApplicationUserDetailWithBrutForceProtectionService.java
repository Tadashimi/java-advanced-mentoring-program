package com.epam.homework2.auth;

import com.epam.homework2.bruteforceprotection.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional
public class Homework2ApplicationUserDetailWithBrutForceProtectionService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    public Homework2ApplicationUserDetailWithBrutForceProtectionService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.userRepository = userRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new LockedException("Account is blocked");
        }

        User user = userRepository.findByUsername(s);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Cannot find user bu username - " + s);
        }
        List<AuthGroup> authGroupList = authGroupRepository.findByUsername(s);
        return new Homework2ApplicationUserPrincipal(user, authGroupList);
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
