package com.covidtest.orderms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity used to map a ShopOrderEntry in the database
 */
@Entity
public class ShopOrderEntry {

    /**
     * Id of the shop order entry
     * Auto-generated primary key
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The product id
     */
    private Long productId;

    /**
     * The product quantity
     */
    private int quantity;

    /**
     * Get the ShopOrderEntry id
     *
     * @return The ShopOrderEntry id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ShopOrderEntry id
     *
     * @param id The new ShopOrderEntry id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the product id
     *
     * @return The product id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set the product id
     *
     * @param productId The new product id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Get the product quantity
     *
     * @return The product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the product quantity
     *
     * @param quantity The new product quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
