package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.OrderMsFeignClient;
import com.covidtest.frontend.model.FullShopOrder;
import com.covidtest.frontend.model.ShopOrder;
import com.covidtest.frontend.service.AuthService;
import com.covidtest.frontend.service.FullShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DeliveryController {

    @Autowired
    private OrderMsFeignClient orderMsFeignClient;

    @Autowired
    private AuthService authService;

    @Autowired
    private FullShopOrderService fullShopOrderService;

    @GetMapping(path = "/delivery")
    public String home(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        Iterable<ShopOrder> unassignedOrders = orderMsFeignClient.getUnassignedOrders(token);
        List<FullShopOrder> unassignedFullOrders = fullShopOrderService.completeOrdersWithFullProductData(unassignedOrders, token);

        Iterable<ShopOrder> assignedOrders = orderMsFeignClient.getOrdersToDeliver(token);
        List<FullShopOrder> assignedFullOrders = fullShopOrderService.completeOrdersWithFullProductData(assignedOrders, token);

        model.addAttribute("unassignedOrders", unassignedFullOrders);
        model.addAttribute("toDeliverOrders", assignedFullOrders);

        return "delivery/home";
    }

    @PostMapping(path = "/delivery/{id}/assign")
    public RedirectView assign(HttpServletRequest request, @PathVariable Long id) {
        String token = authService.getToken(request);

        ShopOrder order = orderMsFeignClient.assign(token, id);

        return new RedirectView("/delivery");
    }

    @PostMapping(path = "/delivery/{id}/delivered")
    public RedirectView markAsDelivered(HttpServletRequest request, @PathVariable Long id) {
        String token = authService.getToken(request);

        ShopOrder order = orderMsFeignClient.markAsDelivered(token, id);

        return new RedirectView("/delivery");
    }
}
