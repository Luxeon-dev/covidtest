package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.KeycloakUser;
import com.covidtest.frontend.model.KeycloakUserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client in charge of requests to the Keycloak API
 */
@FeignClient(name = "keycloak", url = "localhost:8081/auth/admin/realms/covidtest")
public interface KeycloakFeignClient {

    /**
     * Get all users
     *
     * @param token User bearer token
     *
     * @return The list of users
     */
    @GetMapping(path = "/users")
    List<KeycloakUser> getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get a user by id
     *
     * @param token User bearer token
     * @param id The id of the user to retrieve
     *
     * @return The user
     */
    @GetMapping(path = "/users/{id}")
    KeycloakUser getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    /**
     * Get user by username
     *
     * @param token User bearer token
     * @param username The name of the user to retrieve
     *
     * @return A list containing the user
     */
    @GetMapping(path = "/users?briefRepresentation=true&exact=true&username={username}")
    List<KeycloakUser> getUserByUsername(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String username);

    /**
     * Get all Keycloak Realm roles
     *
     * @param token User bearer token
     *
     * @return The list of roles
     */
    @GetMapping(path = "/roles")
    List<KeycloakUserRole> getAllRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get the roles of an user
     *
     * @param token User bearer token
     * @param id The id of the user
     *
     * @return The list of roles
     */
    @GetMapping(path = "/users/{id}/role-mappings/realm")
    List<KeycloakUserRole> getUserRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    /**
     * Create an user
     *
     * @param token User bearer token
     * @param user The user to register
     */
    @PostMapping(path = "/users")
    void createUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody KeycloakUser user);

    /**
     * Add roles to an user
     *
     * @param token User bearer token
     * @param id The id of the user to update
     * @param roles The lit of roles to add
     */
    @PostMapping(path = "/users/{id}/role-mappings/realm")
    void addRolestoUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody List<KeycloakUserRole> roles);

    /**
     * Update an user
     *
     * @param token User bearer token
     * @param id The id of the user to update
     * @param user The new user data
     *
     * @return The updated user
     */
    @PutMapping(path = "/users/{id}")
    KeycloakUser updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody KeycloakUser user);

    /**
     * Delete an user
     *
     * @param token User bearer token
     * @param id The id of the user to delete
     */
    @DeleteMapping(path = "/users/{id}")
    void deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    /**
     * Remove roles of an user
     *
     * @param token User bearer token
     * @param id The id of the user to update
     * @param roles The list of roles to remove
     */
    @DeleteMapping(path = "/users/{id}/role-mappings/realm")
    void deleteRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody List<KeycloakUserRole> roles);
}
