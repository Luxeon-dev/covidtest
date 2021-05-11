package com.covidtest.frontend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of an order with full Product details
 */
public class FullShopOrder {

    /**
     * Id of the order
     */
    private Long id;

    /**
     * User of the order
     */
    private String user;

    /**
     * Delivery address of the order
     */
    private String deliveryAddress;

    /**
     * Order date
     */
    private Date orderedAt;

    /**
     * List of lines of the order
     */
    private List<FullShopOrderEntry> orderEntries;

    /**
     * Delivery status of the order
     */
    private boolean delivered;

    /**
     * User in charge of the delivery of the order
     */
    private String deliveryMan;

    /**
     * Constructor
     *
     * @param order Initial order which need to be converted
     */
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

    /**
     * Get order id
     *
     * @return Order id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set order id
     *
     * @param id New order id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get order user
     *
     * @return Order user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set order user
     * @param user New order user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Get delivery address
     *
     * @return Delivery address
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Set delivrey address
     *
     * @param deliveryAddress New delivery address
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Get order date
     *
     * @return Order date
     */
    public Date getOrderedAt() {
        return orderedAt;
    }

    /**
     * Set order date
     * @param orderedAt New order date
     */
    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    /**
     * Get order lines
     *
     * @return Order lines
     */
    public List<FullShopOrderEntry> getOrderEntries() {
        return orderEntries;
    }

    /**
     * Set order lines
     *
     * @param orderEntries New order lines
     */
    public void setOrderEntries(List<FullShopOrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    /**
     * Get delivery status
     *
     * @return Delivrey status
     */
    public boolean getDelivered() {
        return delivered;
    }

    /**
     * Set delivrey status
     *
     * @param delivered New delivery status
     */
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    /**
     * Get delivery man
     *
     * @return Order delivery man
     */
    public String getDeliveryMan() {
        return deliveryMan;
    }

    /**
     * Set delivery man
     *
     * @param deliveryMan New order delivery man
     */
    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
}
