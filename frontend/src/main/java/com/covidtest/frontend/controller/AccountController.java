package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.OrderMsFeignClient;
import com.covidtest.frontend.model.*;
import com.covidtest.frontend.service.AuthService;
import com.covidtest.frontend.service.FullShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private OrderMsFeignClient orderMsFeignClient;

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
}
