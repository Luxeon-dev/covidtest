package com.covidtest.orderms.dto;

public class ShopOrderDto extends UnidentifiedShopOrderDto {

    /**
     * Id of the shop order
     * Auto-generated primary key
     */
    private Long id;

    /**
     * Get the ShopOrder id
     *
     * @return The ShopOrder id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ShopOrder id
     *
     * @param id The new ShopOrder id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
