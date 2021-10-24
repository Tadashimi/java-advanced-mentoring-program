package com.epam.homework2.bruteforceprotection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 3;

    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    public void loginSucceeded(String address) {
        LoginAttempt loginAttempt = loginAttemptRepository.findByAddress(address);
        if (nonNull(loginAttempt)) {
            loginAttemptRepository.delete(loginAttempt);
        }
    }

    public void loginFailed(String address) {
        LoginAttempt loginAttempt = loginAttemptRepository.findByAddress(address);
        if (isNull(loginAttempt)) {
            loginAttemptRepository.save(new LoginAttempt(address, 0, false));
        } else {
            int newCount = loginAttempt.getCount() + 1;
            loginAttempt.setCount(newCount);
            if (newCount >= MAX_ATTEMPT) {
                loginAttempt.setBlocked(true);
            }
            loginAttemptRepository.save(loginAttempt);
        }
    }

    public boolean isBlocked(String address) {
        LoginAttempt loginAttempt = loginAttemptRepository.findByAddress(address);
        if (nonNull(loginAttempt)) {
            return loginAttempt.isBlocked();
        }
        return false;
    }

    public List<LoginAttempt> getBlockedAddress() {
        return loginAttemptRepository.findAllBlocked();
    }
}
