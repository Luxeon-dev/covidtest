package com.covidtest.frontend.model;

import java.util.List;

public class KeycloakUser {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private List<String> realmRoles;
    private List<KeycloakUserCredential> credentials;

    public KeycloakUser() {
        this.enabled = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRealmRoles() {
        return realmRoles;
    }

    public void setRealmRoles(List<String> realmRoles) {
        this.realmRoles = realmRoles;
    }

    public List<KeycloakUserCredential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<KeycloakUserCredential> credentials) {
        this.credentials = credentials;
    }
}
