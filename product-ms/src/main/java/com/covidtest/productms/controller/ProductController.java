package com.covidtest.productms.controller;

import com.covidtest.productms.dto.ProductDto;
import com.covidtest.productms.dto.UnidentifiedProductDto;
import com.covidtest.productms.model.Product;
import com.covidtest.productms.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * RestController
 * Handles all the CRUD requests for Product entity
 */
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    /**
     * Service used to request the Product table
     */
    @Autowired
    private ProductService productService;

    /**
     * Model mapper for conversion between Entity and Dto
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all products
     *
     * @return The list of the products registered in the database
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> getAllProducts() {
        Iterable<Product> products = productService.getProducts();

        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(p -> productDtos.add(toDto(p)));

        return productDtos;
    }

    /**
     * Get an specific product by its id
     *
     * @param id The id of the product to retrieve
     *
     * @return The book corresponding to the id
     */
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productService.getProduct(id);

        return toDto(product);
    }

    /**
     * Create a product
     *
     * @param unidentifiedProductDto The product to register
     *
     * @return The registered product with its id
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto postProduct(@RequestBody UnidentifiedProductDto unidentifiedProductDto) {
        Product product = toEntity(unidentifiedProductDto);
        Product createdProduct = productService.createProduct(product);

        return toDto(createdProduct);
    }

    /**
     * Update a product
     *
     * @param unidentifiedProductDto The new data for the product
     * @param id The id of the product to update
     *
     * @return The updated product with its id
     */
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto putProduct(@RequestBody UnidentifiedProductDto unidentifiedProductDto, @PathVariable Long id) {
        Product product = toEntity(unidentifiedProductDto);
        Product updatedProduct = productService.updateProduct(id, product);

        return toDto(updatedProduct);
    }

    /**
     * Delete a product
     *
     * @param id The id of the product to delete
     */
    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    /**
     * Convert a Product entity to an identified Product Dto
     *
     * @param product The Product entity to convert
     *
     * @return The identified Product Dto corresponding
     */
    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    /**
     * Convert an unidentified Product Dto to a Product entity
     *
     * @param unidentifiedProductDto The Product Dto to convert
     *
     * @return The Product entity corresponding
     */
    private Product toEntity(UnidentifiedProductDto unidentifiedProductDto) {
        return modelMapper.map(unidentifiedProductDto, Product.class);
    }
}
