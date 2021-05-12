package com.covidtest.frontend.controller;

import com.covidtest.frontend.feign.ApiGatewayFeignClient;
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

/**
 * Controller in charge of the shop section
 */
@Controller
public class ShopController {

    /**
     * OpenFeign client for the API Gateway
     */
    @Autowired
    private ApiGatewayFeignClient apiGatewayFeignClient;

    /**
     * AuthService for the token
     */
    @Autowired
    private AuthService authService;

    /**
     * Redirect root to /shop
     *
     * @return Redirection to the shop
     */
    @GetMapping(path = "/")
    public RedirectView home() {
        return new RedirectView("/shop");
    }

    /**
     * Display the products of the shop
     *
     * @param request The request
     * @param model The model to pass data to the view
     *
     * @return The shop home view template
     */
    @GetMapping(path = "/shop")
    public String shop(HttpServletRequest request, Model model) {
        String token = authService.getToken(request);

        List<Product> products = apiGatewayFeignClient.getAllProducts(token);

        model.addAttribute("products", products);

        return "shop/home";
    }

    /**
     * Display the details of a product
     *
     * @param request The request
     * @param id The id of the product
     * @param model The model to pass data to the view
     * @param shoppingCartEntry An empty shopping cart entry in case of "Add to cart"
     *
     * @return The product details view template
     */
    @GetMapping(path = "/shop/product/{id}")
    public String productDetails(HttpServletRequest request, @PathVariable Long id, Model model, ShoppingCartEntry shoppingCartEntry) {
        String token = authService.getToken(request);

        Product product = apiGatewayFeignClient.getProductById(token, id);

        model.addAttribute("product", product);
        model.addAttribute("shoppingCartEntry", shoppingCartEntry);

        return "shop/product";
    }

    /**
     * Display the cart
     *
     * @param request The request
     * @param model The model to pass data to the view
     * @param order An empty order in case of submit
     *
     * @return The shopping cart view template
     */
    @GetMapping(path = "/shop/cart")
    public String getShoppingCart(HttpServletRequest request, Model model, ShopOrder order) {
        String token = authService.getToken(request);

        Optional<ShoppingCart> optionalCart = apiGatewayFeignClient.getShoppingCart(token);

        List<Map<String, Object>> cartEntries = new ArrayList<>();

        if (optionalCart.isPresent()) {
            for (ShoppingCartEntry entry: optionalCart.get().getEntries()) {

                Product product = apiGatewayFeignClient.getProductById(token, entry.getProductId());

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

    /**
     * Submit a product to the cart
     *
     * @param request The request
     * @param entry The shopping cart entry to register (product + quantity)
     *
     * @return Redirection to the cart
     */
    @PostMapping(path = "/shop/cart")
    public RedirectView addProductToCart(HttpServletRequest request, ShoppingCartEntry entry) {
        String token = authService.getToken(request);

        apiGatewayFeignClient.addProductToCart(token, entry);

        return new RedirectView("/shop/cart");
    }

    /**
     * Submit the cart and register the order
     *
     * @param request The request
     * @param order The order to register
     *
     * @return Redirection to the account
     */
    @PostMapping(path = "/shop/order")
    public RedirectView order(HttpServletRequest request, ShopOrder order) {
        String token = authService.getToken(request);

        order = apiGatewayFeignClient.postOrder(token, order);

        if (order.getId() != null) {
            apiGatewayFeignClient.deleteShoppingCart(token);
        }

        return new RedirectView("/account");
    }
}
