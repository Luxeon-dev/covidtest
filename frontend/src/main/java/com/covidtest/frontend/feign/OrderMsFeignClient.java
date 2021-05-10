package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.ShopOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "order-ms")
@RequestMapping(path = "/api/v1/orders")
public interface OrderMsFeignClient {

    @GetMapping
    Iterable<ShopOrder> getOrdersByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/all")
    Iterable<ShopOrder> getAllOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/unassigned")
    Iterable<ShopOrder> getUnassignedOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/assigned")
    Iterable<ShopOrder> getOrdersToDeliver(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping
    ShopOrder postOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ShopOrder order);

    @PutMapping(path = "/{id}/assign")
    ShopOrder assign(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    @PutMapping(path = "/{id}/delivered")
    ShopOrder markAsDelivered(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);
}
