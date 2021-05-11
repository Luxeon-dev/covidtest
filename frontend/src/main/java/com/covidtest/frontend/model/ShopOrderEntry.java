package com.covidtest.frontend.model;

/**
 * Representation of an order line with only product id
 */
public class ShopOrderEntry {

    /**
     * Order line id
     */
    private Long id;

    /**
     * Order line product
     */
    private Long productId;

    /**
     * Order line product quantity
     */
    private int quantity;

    /**
     * Get order line id
     *
     * @return Order line id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set order line id
     *
     * @param id New order line id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get order line product id
     *
     * @return Order line product id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set order line product id
     *
     * @param productId New order line product id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Get order line product quantity
     *
     * @return Order line product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set order line product quantity
     *
     * @param quantity New order line product quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
