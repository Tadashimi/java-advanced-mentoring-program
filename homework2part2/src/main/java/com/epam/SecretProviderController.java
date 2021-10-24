package com.epam;

import com.epam.model.Secret;
import com.epam.model.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SecretProviderController {

    @Autowired
    SecretService secretService;

    @GetMapping("/secret")
    public String getSecret() {
        return "secret";
    }

    @PostMapping("/secret")
    public String postSecret(@RequestParam String secretData) {
        Secret secret = secretService.saveSecret(secretData);
        return "localhost:8080/" + secret.getSecretId();
    }

    @GetMapping("/secret/{secretId}")
    public String getSecret(@PathVariable String secretId) {
        Secret secret = secretService.readSecret(secretId);
        return secret.getSecretData();
    }
}
