package com.covidtest.frontend.feign;

import com.covidtest.frontend.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "product-ms")
@RequestMapping(path = "/api/v1/products")
public interface ProductMsFeignClient {

    @GetMapping
    List<Product> getAllProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/{id}")
    Product getProductById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);

    @PostMapping
    Product postProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Product product);

    @PutMapping(path = "/{id}")
    Product putProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Product product, @PathVariable Long id);

    @DeleteMapping(path = "/{id}")
    void deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id);
}
