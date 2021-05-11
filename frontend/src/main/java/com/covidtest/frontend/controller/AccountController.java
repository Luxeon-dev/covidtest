package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.KeycloakFeignClient;
import com.covidtest.frontend.feign.OrderMsFeignClient;
import com.covidtest.frontend.model.*;
import com.covidtest.frontend.service.AuthService;
import com.covidtest.frontend.service.FullShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller in charge of account section
 */
@Controller
public class AccountController {

    /**
     * OpenFeign client for Order Microservice
     */
    @Autowired
    private OrderMsFeignClient orderMsFeignClient;

    /**
     * OpenFeign client for Keycloak API
     */
    @Autowired
    private KeycloakFeignClient keycloakFeignClient;

    /**
     * Service to convert standard order in full order with full product infos
     */
    @Autowired
    private FullShopOrderService fullShopOrderService;

    /**
     * AuthService for the token
     */
    @Autowired
    private AuthService authService;

    /**
     * Display the account page with orders for the connected user and the possibility to become a delivery man
     *
     * @param request The request
     * @param model The model to pass data to the view
     *
     * @return The account view template
     */
    @GetMapping(path = "/account")
    public String account(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        Iterable<ShopOrder> orders = orderMsFeignClient.getOrdersByUser(token);

        List<FullShopOrder> fullOrders = fullShopOrderService.completeOrdersWithFullProductData(orders, token);

        model.addAttribute("orders", fullOrders);

        return "account/home";
    }

    /**
     * Add the delivery_man role to the user
     *
     * @param request The request
     *
     * @return Redirection to the logout page (needed to refresh the roles of the user)
     */
    @PostMapping(path = "/account/delivery")
    public RedirectView setDeliveryMan(HttpServletRequest request) {
        String token = authService.getToken(request);
        String userId = authService.getUserId(request);

        List<KeycloakUserRole> keycloakUserRoles = keycloakFeignClient.getAllRoles(token);
        List<KeycloakUserRole> deliveryManRole = keycloakUserRoles.stream().filter(role -> role.getName().equals("delivery_man")).collect(Collectors.toList());

        keycloakFeignClient.addRolestoUser(token, userId, deliveryManRole);

        return new RedirectView("/logout");
    }
}
