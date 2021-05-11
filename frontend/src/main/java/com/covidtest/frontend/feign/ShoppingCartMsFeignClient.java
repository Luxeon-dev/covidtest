package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.ShoppingCart;
import com.covidtest.frontend.model.ShoppingCartEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Feign client in charge of requests to the ShoppingCart microservice
 */
@FeignClient(value = "shoppingcart-ms")
@RequestMapping(path = "/api/v1/cart")
public interface ShoppingCartMsFeignClient {

    /**
     * Get the cart of the connected user
     *
     * @param token User bearer token
     *
     * @return The shopping cart of the user or null if he doesn't already have a cart (i.e. no product in the cart)
     */
    @GetMapping
    Optional<ShoppingCart> getShoppingCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Add product to the connected user's cart
     *
     * @param token User bearer token
     * @param entry The line to add (product + quantity)
     *
     * @return The updated shopping cart
     */
    @PostMapping
    ShoppingCart addProductToCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ShoppingCartEntry entry);

    /**
     * Delete the connected user cart
     *
     * @param token User bearer token
     */
    @DeleteMapping
    void deleteShoppingCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
