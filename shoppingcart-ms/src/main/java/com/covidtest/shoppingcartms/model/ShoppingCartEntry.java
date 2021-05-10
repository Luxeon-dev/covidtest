package com.covidtest.shoppingcartms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity used to map a ShoppingCartEntry in the database
 */
@Entity
public class ShoppingCartEntry {

    /**
     * Id of the shopping cart entry
     * Auto-generated primary key
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Id of the product of this entry
     */
    private Long productId;

    /**
     * Quantity of the product
     */
    private int quantity;

    /**
     * Get the shopping cart entry id
     *
     * @return The shopping cart entry id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the shopping cart entry id
     *
     * @param id The new shopping cart entry id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the shopping cart entry product id
     *
     * @return The shopping cart entry product id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set the shopping cart entry product id
     *
     * @param productId The new shopping cart entry product id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Get the shopping cart entry product quantity
     *
     * @return The shopping cart entry product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the shopping cart entry product quantity
     *
     * @param quantity The shopping cart entry product quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
