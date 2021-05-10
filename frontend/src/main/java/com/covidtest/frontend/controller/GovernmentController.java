package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.OrderMsFeignClient;
import com.covidtest.frontend.feign.ProductMsFeignClient;
import com.covidtest.frontend.model.FullShopOrder;
import com.covidtest.frontend.model.Product;
import com.covidtest.frontend.model.ShopOrder;
import com.covidtest.frontend.service.AuthService;
import com.covidtest.frontend.service.FullShopOrderService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GovernmentController {

    @Autowired
    private OrderMsFeignClient orderMsFeignClient;

    @Autowired
    private FullShopOrderService fullShopOrderService;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/government")
    public String getAllOrders(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        Iterable<ShopOrder> orders = orderMsFeignClient.getAllOrders(token);

        List<FullShopOrder> fullOrders = fullShopOrderService.completeOrdersWithFullProductData(orders, token);

        model.addAttribute("orders", fullOrders);

        return "government/home";
    }
}
