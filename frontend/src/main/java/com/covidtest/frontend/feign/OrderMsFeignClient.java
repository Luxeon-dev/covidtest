package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.ShopOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client in charge of requests to the Order microservice
 */
@FeignClient(value = "order-ms")
@RequestMapping(path = "/api/v1/orders")
public interface OrderMsFeignClient {

    /**
     * Get orders of the connected user
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping
    Iterable<ShopOrder> getOrdersByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get all orders
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/all")
    Iterable<ShopOrder> getAllOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get all orders which are not already assigned to a delivery man for the delivery
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/unassigned")
    Iterable<ShopOrder> getUnassignedOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);


    /**
     * Get all orders assigned to the connected delivery man but not delivered for the moment
     *
     * @param token User bearer token
     *
     * @return The list of orders
     */
    @GetMapping(path = "/assigned")
    Iterable<ShopOrder> getOrdersToDeliver(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Add an order
     *
     * @param token User bearer token
     * @param order The order to register
     *
     * @return The registered order
     */
    @PostMapping
    ShopOrder postOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ShopOrder order);

    /**
     * Assign the connected delivery man to an order
     *
     * @param token User bearer token
     * @param id Id of the order
     *
     * @return The updated order
     */
    @PutMapping(path = "/{id}/assign")
    ShopOrder assign(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    /**
     * Set an order as delivered
     *
     * @param token User bearer token
     * @param id Id of the order
     *
     * @return The updated order
     */
    @PutMapping(path = "/{id}/delivered")
    ShopOrder markAsDelivered(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);
}
