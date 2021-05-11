package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client in charge of requests to the Product microservice
 */
@FeignClient(value = "product-ms")
@RequestMapping(path = "/api/v1/products")
public interface ProductMsFeignClient {

    /**
     * Get all products
     *
     * @param token User bearer token
     *
     * @return The list of products
     */
    @GetMapping
    List<Product> getAllProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get a product by id
     *
     * @param token User bearer token
     * @param id The product id
     *
     * @return The product
     */
    @GetMapping(path = "/{id}")
    Product getProductById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    /**
     * Add a product
     *
     * @param token User bearer token
     * @param product The product to register
     *
     * @return The registered product
     */
    @PostMapping
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
    @PutMapping(path = "/{id}")
    Product putProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Product product, @PathVariable Long id);

    /**
     * Delete a product
     *
     * @param token User bearer token
     * @param id The id of the product to delete
     */
    @DeleteMapping(path = "/{id}")
    void deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);
}
