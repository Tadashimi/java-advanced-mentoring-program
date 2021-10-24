package com.epam.homework2.bruteforceprotection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Homework2ApplicationAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messages;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
        if (exception.getMessage().equalsIgnoreCase("blocked")) {
            response.sendRedirect("/blockedError");
        }
        if (exception.getClass().equals(RuntimeException.class)) {
            response.sendRedirect("/blockedError");
        }
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "User is blocked");
    }
}