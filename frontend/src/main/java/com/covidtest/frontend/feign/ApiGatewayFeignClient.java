package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.Product;
import com.covidtest.frontend.model.ShopOrder;
import com.covidtest.frontend.model.ShoppingCart;
import com.covidtest.frontend.model.ShoppingCartEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Feign client in charge of requests to the API Gateway
 */
@FeignClient(value = "gateway")
@RequestMapping(path = "/api/v1")
public interface ApiGatewayFeignClient {

    /**
     * Get all products
     *
     * @param token User bearer token
     *
     * @return The list of products
     */
    @GetMapping(path = "/products")
    List<Product> getAllProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get a product by id
     *
     * @param token User bearer token
     * @param id The product id
     *
     * @return The product
     */
    @GetMapping(path = "/products/{id}")
    Product getProductById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    /**
     * Add a product
     *
     * @param token User bearer token
     * @param product The product to register
     *
     * @return The registered product
     */
    @PostMapping(path = "/products")
    Product postProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Product product);

    /**
     * Update a product
     *
     * @param token User bearer token
     * @param product The new product data
     * @param id The id of the product to update
     *
     * @return The updated product
     */
    @PutMapping(path = "/products/{id}")
    Product putProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Product product, @PathVariable Long id);

    /**
     * Delete a product
     *
     * @param token User bearer token
     * @param id The id of the product to delete
     */
    @DeleteMapping(path = "/products/{id}")
    void deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    /**
     * Get the cart of the connected user
     *
     * @param token User bearer token
     *
     * @return The shopping cart of the user or null if he doesn't already have a cart (i.e. no product in the cart)
     */
    @GetMapping(path = "cart")
    Optional<ShoppingCart> getShoppingCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Add product to the connected user's cart
     *
     * @param token User bearer token
     * @param entry The line to add (product + quantity)
     *
     * @return The updated shopping cart
     */
    @PostMapping(path = "cart")
    ShoppingCart addProductToCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ShoppingCartEntry entry);

    /**
     * Delete the connected user cart
     *
     * @param token User bearer token
     */
    @DeleteMapping(path = "cart")
    void deleteShoppingCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get orders of the connected user
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/orders")
    Iterable<ShopOrder> getOrdersByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get all orders
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/orders/all")
    Iterable<ShopOrder> getAllOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get all orders which are not already assigned to a delivery man for the delivery
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/orders/unassigned")
    Iterable<ShopOrder> getUnassignedOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);


    /**
     * Get all orders assigned to the connected delivery man but not delivered for the moment
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/orders/assigned")
    Iterable<ShopOrder> getOrdersToDeliver(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Add an order
     *
     * @param token User bearer token
     * @param order The order to register
     *
     * @return The registered order
     */
    @PostMapping(path = "/orders")
    ShopOrder postOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ShopOrder order);

    /**
     * Assign the connected delivery man to an order
     *
     * @param token User bearer token
     * @param id Id of the order
     *
     * @return The updated order
     */
    @PutMapping(path = "/orders/{id}/assign")
    ShopOrder assign(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    /**
     * Set an order as delivered
     *
     * @param token User bearer token
     * @param id Id of the order
     *
     * @return The updated order
     */
    @PutMapping(path = "/orders/{id}/delivered")
    ShopOrder markAsDelivered(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);
}
