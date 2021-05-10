package com.covidtest.productms.dto;

public class ProductDto extends UnidentifiedProductDto {

    /**
     * Id of the product
     */
    private Long id;

    /**
     * Get the product id
     *
     * @return The product id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the product id
     *
     * @param id The new product id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
