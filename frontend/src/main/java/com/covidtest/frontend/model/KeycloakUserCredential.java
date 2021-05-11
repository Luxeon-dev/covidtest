package com.covidtest.frontend.model;

/**
 * Representation of user credential
 */
public class KeycloakUserCredential {

    /**
     * Credential value
     */
    private String value;

    /**
     * Get credential value
     *
     * @return Credential value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set credential value
     *
     * @param value New credential value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
