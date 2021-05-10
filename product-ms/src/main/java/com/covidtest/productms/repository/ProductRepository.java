package com.covidtest.productms.repository;

import com.covidtest.productms.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository in charge of the requests for the product table
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}
