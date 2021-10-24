package com.epam.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Long> {

    Secret getBySecretId(String secretId);
}
