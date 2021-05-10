package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.OrderMsFeignClient;
import com.covidtest.frontend.feign.ProductMsFeignClient;
import com.covidtest.frontend.feign.ShoppingCartMsFeignClient;
import com.covidtest.frontend.model.ShopOrder;
import com.covidtest.frontend.model.Product;
import com.covidtest.frontend.model.ShoppingCart;
import com.covidtest.frontend.model.ShoppingCartEntry;
import com.covidtest.frontend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ShopController {

    @Autowired
    private ProductMsFeignClient productMsFeignClient;

    @Autowired
    private ShoppingCartMsFeignClient shoppingCartMsFeignClient;

    @Autowired
    private OrderMsFeignClient orderMsFeignClient;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/")
    public RedirectView home() {
        return new RedirectView("/shop");
    }

    @GetMapping(path = "/shop")
    public String shop(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        List<Product> products = productMsFeignClient.getAllProducts(token);

        model.addAttribute("products", products);

        return "shop/home";
    }

    @GetMapping(path = "/shop/product/{id}")
    public String productDetails(HttpServletRequest request, @PathVariable Long id, Model model, ShoppingCartEntry shoppingCartEntry) {
        String token = authService.getToken(request);

        Product product = productMsFeignClient.getProductById(token, id);

        model.addAttribute("product", product);
        model.addAttribute("shoppingCartEntry", shoppingCartEntry);

        return "shop/product";
    }

    @GetMapping(path = "/shop/cart")
    public String getShoppingCart(HttpServletRequest request, Model model, ShopOrder order) {
        String token = authService.getToken(request);

        Optional<ShoppingCart> optionalCart = shoppingCartMsFeignClient.getShoppingCart(token);

        List<Map<String, Object>> cartEntries = new ArrayList<>();

        if (optionalCart.isPresent()) {
            for (ShoppingCartEntry entry: optionalCart.get().getEntries()) {

                Product product = productMsFeignClient.getProductById(token, entry.getProductId());

                Map<String, Object> cartEntry = new HashMap<>();
                cartEntry.put("product", product);
                cartEntry.put("quantity", entry.getQuantity());

                cartEntries.add(cartEntry);
            }
        }

        model.addAttribute("entries", cartEntries);
        model.addAttribute("order", order);

        return "shop/cart";
    }

    @PostMapping(path = "/shop/cart")
    public RedirectView addProductToCart(HttpServletRequest request, ShoppingCartEntry entry) {
        String token = authService.getToken(request);

        shoppingCartMsFeignClient.addProductToCart(token, entry);

        return new RedirectView("/shop/cart");
    }

    @PostMapping(path = "/shop/order")
    public RedirectView order(HttpServletRequest request, ShopOrder order) {
        String token = authService.getToken(request);

        order = orderMsFeignClient.postOrder(token, order);

        if (order.getId() != null) {
            shoppingCartMsFeignClient.deleteShoppingCart(token);
        }

        return new RedirectView("/account");
    }
}
