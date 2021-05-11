package com.covidtest.frontend.service;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Service to get connected user infos
 */
@Service
public class AuthService {

    /**
     * Get user bearer token
     *
     * @param request The request
     *
     * @return The token as string
     */
    public String getToken(HttpServletRequest request) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        return "Bearer " + context.getTokenString();
    }

    /**
     * Get user id
     *
     * @param request The request
     *
     * @return The user id
     */
    public String getUserId(HttpServletRequest request) {
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        return context.getToken().getSubject();
    }
}
