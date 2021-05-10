package com.covidtest.frontend.service;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    public String getToken(HttpServletRequest request) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        return "Bearer " + context.getTokenString();
    }

    public String getUserId(HttpServletRequest request) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        return context.getToken().getSubject();
    }
}
