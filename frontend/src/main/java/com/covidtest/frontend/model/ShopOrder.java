package com.covidtest.frontend.model;

import java.util.Date;
import java.util.List;

public class ShopOrder {

    private Long id;

    private String user;

    private String deliveryAddress;

    private Date orderedAt;

    private List<ShopOrderEntry> orderEntries;

    private boolean delivered;

    private String deliveryMan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public List<ShopOrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(List<ShopOrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
}
