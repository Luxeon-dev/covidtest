package com.covidtest.frontend.model;

/**
 * Representation of an order line with full product details
 */
public class FullShopOrderEntry {

    /**
     * Order line id
     */
    private Long id;

    /**
     * Order line product
     */
    private Product product;

    /**
     * Order line product quantity
     */
    private int quantity;

    /**
     * Constructor
     *
     * @param orderEntry ShopOrderEntry that need to be converted
     */
    public FullShopOrderEntry(ShopOrderEntry orderEntry) {
        this.id = orderEntry.getId();
        this.quantity = orderEntry.getQuantity();
    }

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
     * Get order line product
     *
     * @return Order line product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set order line product
     *
     * @param product New order line product
     */
    public void setProduct(Product product) {
        this.product = product;
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
