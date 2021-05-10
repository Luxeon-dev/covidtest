package com.covidtest.productms.service;

import com.covidtest.productms.model.Product;
import com.covidtest.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Service which handle Product CRUD
 */
@Service
public class ProductService {

    /**
     * Product repository to handle database requests
     */
    @Autowired
    ProductRepository productRepository;

    /**
     * Get all products
     *
     * @return All database products
     */
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Get a product by id
     *
     * @param id The product id
     *
     * @return The product corresponding with the id
     */
    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        return product.orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Create a product
     *
     * @param product The product to register
     *
     * @return The registered product
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Update a product
     *
     * @param id The id of the product to update
     * @param product The new data
     *
     * @return The updated product
     */
    public Product updateProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Delete a product
     *
     * @param id The id of the product to delete
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
