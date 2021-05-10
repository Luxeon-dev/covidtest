package com.covidtest.orderms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entity used to map a ShopOrder in the database
 */
@Entity
public class ShopOrder {

    /**
     * Id of the shop order
     * Auto-generated primary key
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the user who makes the order
     */
    private String user;

    /**
     * The delivery address of the order
     */
    private String deliveryAddress;

    /**
     * The Date of the order
     */
    private Date orderedAt;

    /**
     * The lines of the order (product + quantity)
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShopOrderEntry> orderEntries;

    /**
     * The status of the delivery (delivered or not)
     */
    private boolean delivered;

    /**
     * The user name in charge of the delivery
     */
    private String deliveryMan;

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

    /**
     * Get the ShopOrder user
     *
     * @return The ShopOrder user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the ShopOrder user
     *
     * @param user The new ShopOrder user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Get the ShopOrder delivery address
     *
     * @return The delivery address
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Set the ShopOrder delivery address
     *
     * @param deliveryAddress The new delivery address
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Get the ShopOrder order date
     *
     * @return The order date
     */
    public Date getOrderedAt() {
        return orderedAt;
    }

    /**
     * Set the ShopOrder order date
     *
     * @param orderedAt The new order date
     */
    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    /**
     * Get the list of order lines
     *
     * @return The list of order lines
     */
    public List<ShopOrderEntry> getOrderEntries() {
        return orderEntries;
    }

    /**
     * Set the list of order lines
     *
     * @param orderEntries The new list of order lines
     */
    public void setOrderEntries(List<ShopOrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    /**
     * Get the delivery status
     *
     * @return The delivery status
     */
    public boolean getDelivered() {
        return delivered;
    }

    /**
     * Set the delivery status
     *
     * @param delivered The new delivery status
     */
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    /**
     * Get the delivery man in charge of the order
     *
     * @return The delivery man name
     */
    public String getDeliveryMan() {
        return deliveryMan;
    }

    /**
     * Set the delivery man in charge of the order
     *
     * @param deliveryMan The new delivery man name
     */
    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
}
