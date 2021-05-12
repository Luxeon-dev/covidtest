package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.ApiGatewayFeignClient;
import com.covidtest.frontend.model.FullShopOrder;
import com.covidtest.frontend.model.ShopOrder;
import com.covidtest.frontend.service.AuthService;
import com.covidtest.frontend.service.FullShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller in charge of the government section
 */
@Controller
public class GovernmentController {

    /**
     * OpenFeign client for the API Gateway
     */
    @Autowired
    private ApiGatewayFeignClient apiGatewayFeignClient;

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
     * Display a list of all orders
     *
     * @param request The request
     * @param model The model to pass data to the view
     *
     * @return The government home view template
     */
    @GetMapping(path = "/government")
    public String getAllOrders(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        Iterable<ShopOrder> orders = apiGatewayFeignClient.getAllOrders(token);

        List<FullShopOrder> fullOrders = fullShopOrderService.completeOrdersWithFullProductData(orders, token);

        model.addAttribute("orders", fullOrders);

        return "government/home";
    }
}
