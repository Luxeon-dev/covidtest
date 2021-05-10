package com.covidtest.frontend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FullShopOrder {

    private Long id;

    private String user;

    private String deliveryAddress;

    private Date orderedAt;

    private List<FullShopOrderEntry> orderEntries;

    private boolean delivered;

    private String deliveryMan;

    public FullShopOrder(ShopOrder order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.deliveryAddress = order.getDeliveryAddress();
        this.orderedAt = order.getOrderedAt();
        this.delivered = order.getDelivered();
        this.deliveryMan = order.getDeliveryMan();
        this.orderEntries = new ArrayList<>();
        for (ShopOrderEntry orderEntry: order.getOrderEntries()) {
            this.orderEntries.add(new FullShopOrderEntry(orderEntry));
        }
    }

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

    public List<FullShopOrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(List<FullShopOrderEntry> orderEntries) {
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
