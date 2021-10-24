package com.epam.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SecretService {
    private static final Random RANDOM = new Random();

    @Autowired
    SecretRepository secretRepository;

    public Secret saveSecret(String secretData) {
        String secretId = "RandomString" + RANDOM.nextInt();
        Secret secret = new Secret(secretId, secretData);
        secretRepository.save(secret);
        return secret;
    }

    public Secret readSecret(String secretId) {
        Secret secret = secretRepository.getBySecretId(secretId);
        secretRepository.delete(secret);
        return secret;
    }
}
