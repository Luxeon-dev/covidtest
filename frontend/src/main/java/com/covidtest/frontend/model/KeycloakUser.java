package com.covidtest.frontend.model;

import java.util.List;

/**
 * Representation of a Keycloak user
 */
public class KeycloakUser {

    /**
     * User id
     */
    private String id;

    /**
     * User first name
     */
    private String firstName;

    /**
     * User last name
     */
    private String lastName;

    /**
     * User name
     */
    private String username;

    /**
     * User email
     */
    private String email;

    /**
     * User enabled status
     */
    private boolean enabled;

    /**
     * User roles
     */
    private List<String> realmRoles;

    /**
     * List of user credentials
     */
    private List<KeycloakUserCredential> credentials;

    /**
     * Constructor
     */
    public KeycloakUser() {
        this.enabled = true;
    }

    /**
     * Get user id
     *
     * @return User id
     */
    public String getId() {
        return id;
    }

    /**
     * Set user id
     *
     * @param id New user id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get user first name
     *
     * @return User first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set user first name
     *
     * @param firstName New user first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get user last name
     *
     * @return User last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set user last name
     *
     * @param lastName New user last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get username
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get user email
     *
     * @return User email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user email
     *
     * @param email New user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user enabled status
     *
     * @return User enabled status
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set user enabled status
     *
     * @param enabled New user enabled status
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get user roles
     *
     * @return List of user roles
     */
    public List<String> getRealmRoles() {
        return realmRoles;
    }

    /**
     * Set user roles
     *
     * @param realmRoles New list of user roles
     */
    public void setRealmRoles(List<String> realmRoles) {
        this.realmRoles = realmRoles;
    }

    /**
     * Get user credentials
     *
     * @return List of user credentials
     */
    public List<KeycloakUserCredential> getCredentials() {
        return credentials;
    }

    /**
     * Set user credentials
     *
     * @param credentials New list of user credentials
     */
    public void setCredentials(List<KeycloakUserCredential> credentials) {
        this.credentials = credentials;
    }
}
