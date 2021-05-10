package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.KeycloakFeignClient;
import com.covidtest.frontend.feign.OrderMsFeignClient;
import com.covidtest.frontend.model.*;
import com.covidtest.frontend.service.AuthService;
import com.covidtest.frontend.service.FullShopOrderService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AccountController {

    @Autowired
    private OrderMsFeignClient orderMsFeignClient;

    @Autowired
    private KeycloakFeignClient keycloakFeignClient;

    @Autowired
    private FullShopOrderService fullShopOrderService;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/account")
    public String account(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        Iterable<ShopOrder> orders = orderMsFeignClient.getOrdersByUser(token);

        List<FullShopOrder> fullOrders = fullShopOrderService.completeOrdersWithFullProductData(orders, token);

        model.addAttribute("orders", fullOrders);

        return "account/home";
    }

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
