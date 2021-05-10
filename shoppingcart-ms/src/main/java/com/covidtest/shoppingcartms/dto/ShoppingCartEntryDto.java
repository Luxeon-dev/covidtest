package com.covidtest.shoppingcartms.dto;

public class ShoppingCartEntryDto extends UnidentifiedShoppingCartEntryDto {

    /**
     * Id of the shopping cart entry
     */
    private Long id;

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
}
