package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.ShoppingCart;
import com.covidtest.frontend.model.ShoppingCartEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(value = "shoppingcart-ms")
@RequestMapping(path = "/api/v1/cart")
public interface ShoppingCartMsFeignClient {

    @GetMapping
    Optional<ShoppingCart> getShoppingCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping
    ShoppingCart addProductToCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ShoppingCartEntry entry);

    @DeleteMapping
    void deleteShoppingCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
