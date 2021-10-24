package com.epam.homework2;

import com.epam.homework2.bruteforceprotection.LoginAttempt;
import com.epam.homework2.bruteforceprotection.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class Homework2ApplicationController {

    @Autowired
    LoginAttemptService loginAttemptService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "logout_page";
    }

    //non-authenticated access
    @GetMapping(value = {"/", "/about"})
    public String getAbout() {
        return "about";
    }

    //"VIEW_INFO" permission
    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getInfo() {
        return "info";
    }

    //"VIEW_ADMIN" permission
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/blocked")
    public String getBlocked(Model model) {
        Map<String,  List<LoginAttempt>> map = new HashMap<>();
        List<LoginAttempt> blockedAddress = loginAttemptService.getBlockedAddress();
        map.put("users", blockedAddress);
        model.addAllAttributes(map);
        return "/blockedUsers";
    }
}
