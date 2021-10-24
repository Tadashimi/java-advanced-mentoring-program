package com.epam.homework2.bruteforceprotection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    LoginAttempt findByAddress(String address);

    LoginAttempt save(LoginAttempt attempt);

    @Query("SELECT la FROM LoginAttempt la WHERE la.isBlocked = true")
    List<LoginAttempt> findAllBlocked();

}
