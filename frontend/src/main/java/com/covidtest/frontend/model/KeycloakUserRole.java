package com.covidtest.frontend.model;

/**
 * Representation of a Keycloak role
 */
public class KeycloakUserRole {

    /**
     * Role id
     */
    private String id;

    /**
     * Role name
     */
    private String name;

    /**
     * Get role id
     *
     * @return Role id
     */
    public String getId() {
        return id;
    }

    /**
     * Set role id
     *
     * @param id New role id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get role name
     *
     * @return Role name
     */
    public String getName() {
        return name;
    }

    /**
     * Set role name
     *
     * @param name New role name
     */
    public void setName(String name) {
        this.name = name;
    }
}
