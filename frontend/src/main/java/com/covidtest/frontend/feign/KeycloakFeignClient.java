package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.KeycloakUser;
import com.covidtest.frontend.model.KeycloakUserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "keycloak", url = "localhost:8081/auth/admin/realms/covidtest")
public interface KeycloakFeignClient {

    @GetMapping(path = "/users")
    List<KeycloakUser> getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/users/{id}")
    KeycloakUser getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    @GetMapping(path = "/users?briefRepresentation=true&exact=true&username={username}")
    List<KeycloakUser> getUserByUsername(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String username);

    @GetMapping(path = "/roles")
    List<KeycloakUserRole> getAllRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/users/{id}/role-mappings/realm")
    List<KeycloakUserRole> getUserRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    @PostMapping(path = "/users")
    void createUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody KeycloakUser user);

    @PostMapping(path = "/users/{id}/role-mappings/realm")
    void addRolestoUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody List<KeycloakUserRole> roles);

    @PutMapping(path = "/users/{id}")
    KeycloakUser updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody KeycloakUser user);

    @DeleteMapping(path = "/users/{id}")
    void deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id);

    @DeleteMapping(path = "/users/{id}/role-mappings/realm")
    void deleteRoles(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody List<KeycloakUserRole> roles);
}
