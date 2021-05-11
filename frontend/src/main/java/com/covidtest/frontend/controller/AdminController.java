package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.KeycloakFeignClient;
import com.covidtest.frontend.feign.ProductMsFeignClient;
import com.covidtest.frontend.model.KeycloakUser;
import com.covidtest.frontend.model.KeycloakUserRole;
import com.covidtest.frontend.model.Product;
import com.covidtest.frontend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller in charge of administration section
 */
@Controller
public class AdminController {

    /**
     * OpenFeign client for the product microservice
     */
    @Autowired
    private ProductMsFeignClient productMsFeignClient;

    /**
     * OpenFeign client for the Keycloak API
     */
    @Autowired
    private KeycloakFeignClient keycloakFeignClient;

    /**
     * AuthService for the token
     */
    @Autowired
    private AuthService authService;

    /**
     * Get the landing page of the administration section
     *
     * @return The admin home view template
     */
    @GetMapping(path = "/admin")
    public String home() {
        return "admin/home";
    }

    /* ########################## Product CRUD ########################## */

    /**
     * List all products with actions
     *
     * @param request The request
     * @param model The model to pass data to the view
     *
     * @return The admin products view template
     */
    @GetMapping(path = "/admin/products")
    public String products(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        List<Product> products = productMsFeignClient.getAllProducts(token);

        model.addAttribute("products", products);

        return "admin/products";
    }

    /**
     * Show details about a specific product
     *
     * @param request The request
     * @param model The model to pass data to the view
     * @param id The id of the product
     *
     * @return The admin product details view template
     */
    @GetMapping(path = "/admin/products/{id}")
    public String showProduct(HttpServletRequest request, Model model, @PathVariable Long id) {
        String token = authService.getToken(request);

        Product product = productMsFeignClient.getProductById(token, id);

        model.addAttribute("product", product);

        return "admin/product_show";
    }

    /**
     * Get the form to create a new product
     *
     * @param model The model to pass data to the view
     * @param product Empty product to pass to the form
     *
     * @return The admin new product form view template
     */
    @GetMapping(path = "/admin/products/new")
    public String addProductGet(Model model, Product product) {

        model.addAttribute("product", product);

        return "admin/product_new";
    }

    /**
     * Handle product creation form
     *
     * @param request The request
     * @param product The product to register
     *
     * @return Redirection to the admin product list page
     */
    @PostMapping(path = "/admin/products/new")
    public RedirectView addProductPost(HttpServletRequest request, Product product) {
        String token = authService.getToken(request);

        product = productMsFeignClient.postProduct(token, product);

        return new RedirectView("/admin/products");
    }

    /**
     * Get the form to edit a product
     *
     * @param request The request
     * @param model The model to pass data to the view
     * @param id The id of the product to edit
     *
     * @return The admin edit product form view template
     */
    @GetMapping(path = "/admin/products/edit/{id}")
    public String editProductGet(HttpServletRequest request, Model model, @PathVariable Long id) {
        String token = authService.getToken(request);

        Product product = productMsFeignClient.getProductById(token, id);

        model.addAttribute("product", product);

        return "admin/product_edit";
    }

    /**
     * Handle product edit form
     *
     * @param request The request
     * @param product The new product data to register
     * @param id The id of the product to update
     *
     * @return Redirection to the admin product list page
     */
    @PostMapping(path = "/admin/products/edit/{id}")
    public RedirectView editProductPost(HttpServletRequest request, Product product, @PathVariable Long id) {
        String token = authService.getToken(request);

        product = productMsFeignClient.putProduct(token, product, id);

        return new RedirectView("/admin/products");
    }

    /**
     * Delete a product
     *
     * @param request The request
     * @param id The id of the product to delete
     *
     * @return Redirection to the admin product list page
     */
    @GetMapping(path = "/admin/products/delete/{id}")
    public RedirectView deleteProduct(HttpServletRequest request, @PathVariable Long id) {
        String token = authService.getToken(request);

        productMsFeignClient.deleteProduct(token, id);

        return new RedirectView("/admin/products");
    }

    /* ########################## User CRUD ########################## */

    /**
     * List all users with actions
     *
     * @param request The request
     * @param model The model to pass data to the view
     *
     * @return The admin user list view template
     */
    @GetMapping(path = "/admin/users")
    public String getAllUsers(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        List<KeycloakUser> users = keycloakFeignClient.getAllUsers(token);

        for (KeycloakUser user: users) {
            List<KeycloakUserRole> roles = keycloakFeignClient.getUserRoles(token, user.getId());

            user.setRealmRoles(roles.stream().map(role -> role.getName()).collect(Collectors.toList()));
        }

        model.addAttribute("users", users);

        return "admin/users";
    }

    /**
     * Show details about a specific user
     *
     * @param request The request
     * @param model The model to pass data to the view
     * @param id The id of the user
     *
     * @return The admin user details view template
     */
    @GetMapping(path = "/admin/users/{id}")
    public String showUser(HttpServletRequest request, Model model, @PathVariable String id) {
        String token = authService.getToken(request);

        KeycloakUser user = keycloakFeignClient.getUserById(token, id);
        List<KeycloakUserRole> roles = keycloakFeignClient.getUserRoles(token, id);

        user.setRealmRoles(roles.stream().map(role -> role.getName()).collect(Collectors.toList()));

        model.addAttribute("user", user);

        return "admin/user_show";
    }

    /**
     * Get the form to create a new user
     *
     * @param model The model to pass data to the view
     * @param user Empty user to pass to the form
     *
     * @return The admin new user form view template
     */
    @GetMapping(path = "/admin/user/new")
    public String addUserGet(Model model, KeycloakUser user) {
        model.addAttribute("user", user);

        return "admin/user_new";
    }

    /**
     * Handle user creation form
     *
     * @param request The request
     * @param user The user to register
     *
     * @return Redirection to the admin user list page
     */
    @PostMapping(path = "/admin/user/new")
    public RedirectView addUserPost(HttpServletRequest request, KeycloakUser user) {
        String token = authService.getToken(request);

        List<String> selectedRoles = user.getRealmRoles();
        List<KeycloakUserRole> keycloakUserRoles = keycloakFeignClient.getAllRoles(token);
        List<KeycloakUserRole> rolesToAdd = keycloakUserRoles.stream().filter(role -> selectedRoles.contains(role.getName())).collect(Collectors.toList());

        keycloakFeignClient.createUser(token, user);
        user = keycloakFeignClient.getUserByUsername(token, user.getUsername()).get(0);

        keycloakFeignClient.addRolestoUser(token, user.getId(), rolesToAdd);

        return new RedirectView("/admin/users");
    }

    /**
     * Get the form to edit an user
     *
     * @param request The request
     * @param model The model to pass data to the view
     * @param id The id of the user to edit
     *
     * @return The admin edit user form view template
     */
    @GetMapping(path = "/admin/users/edit/{id}")
    public String editUserGet(HttpServletRequest request, Model model, @PathVariable String id) {
        String token = authService.getToken(request);

        KeycloakUser user = keycloakFeignClient.getUserById(token, id);
        List<KeycloakUserRole> roles = keycloakFeignClient.getUserRoles(token, id);
        user.setRealmRoles(roles.stream().map(role -> role.getName()).collect(Collectors.toList()));

        model.addAttribute("user", user);

        return "admin/user_edit";
    }

    /**
     * Handle user edit form
     *
     * @param request The request
     * @param user The new user data to register
     * @param id The id of the user to update
     *
     * @return Redirection to the admin user list page
     */
    @PostMapping(path = "/admin/users/edit/{id}")
    public RedirectView editUserPost(HttpServletRequest request, KeycloakUser user, @PathVariable String id) {
        String token = authService.getToken(request);

        List<String> selectedRoles = user.getRealmRoles();
        List<KeycloakUserRole> keycloakUserRoles = keycloakFeignClient.getAllRoles(token);
        List<KeycloakUserRole> rolesToAdd = keycloakUserRoles.stream().filter(role -> selectedRoles.contains(role.getName())).collect(Collectors.toList());

        keycloakFeignClient.addRolestoUser(token, id, rolesToAdd);

        List<String> defaultRoles = Arrays.asList("customer", "uma_authorization", "offline_access");

        List<KeycloakUserRole> rolesToRemove = keycloakFeignClient.getUserRoles(token, id).stream()
                .filter(role -> (!selectedRoles.contains(role.getName()) && !defaultRoles.contains(role.getName()))).collect(Collectors.toList());
        keycloakFeignClient.deleteRoles(token, id, rolesToRemove);

        user = keycloakFeignClient.updateUser(token, id, user);

        return new RedirectView("/admin/users");
    }

    /**
     * Delete an user
     *
     * @param request The request
     * @param id The id of the user to delete
     *
     * @return Redirection to the admin user list page
     */
    @GetMapping(path = "/admin/user/delete/{id}")
    public RedirectView deleteUser(HttpServletRequest request, @PathVariable String id) {
        String token = authService.getToken(request);

        keycloakFeignClient.deleteUser(token, id);

        return new RedirectView("/admin/users");
    }
}
