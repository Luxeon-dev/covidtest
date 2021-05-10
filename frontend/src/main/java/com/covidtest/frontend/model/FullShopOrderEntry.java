package com.covidtest.frontend.model;

public class FullShopOrderEntry {

    private Long id;

    private Product product;

    private int quantity;

    public FullShopOrderEntry(ShopOrderEntry orderEntry) {
        this.id = orderEntry.getId();
        this.quantity = orderEntry.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
