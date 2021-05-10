package com.covidtest.shoppingcartms.dto;

public class UnidentifiedShoppingCartEntryDto {

    /**
     * Id of the product of this entry
     */
    private Long productId;

    /**
     * Quantity of the product
     */
    private int quantity;

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
