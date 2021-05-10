package com.covidtest.shoppingcartms.model;

import javax.persistence.*;
import java.util.List;

/**
 * Entity used to map a ShoppingCart in the database
 */
@Entity
public class ShoppingCart {

    /**
     * Id of the shopping cart
     * Auto-generated primary key
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * List of lines/products in the cart
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShoppingCartEntry> entries;

    /**
     * Name of the user who owns this shopping cart
     */
    private String user;

    /**
     * Get the shopping cart id
     *
     * @return The shopping cart id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the shopping cart id
     *
     * @param id The new shopping cart id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the shopping cart lines
     *
     * @return The shopping cart lines
     */
    public List<ShoppingCartEntry> getEntries() {
        return entries;
    }

    /**
     * Set the shopping cart lines
     *
     * @param entries The new shopping cart lines
     */
    public void setEntries(List<ShoppingCartEntry> entries) {
        this.entries = entries;
    }

    /**
     * Get the shopping cart user
     *
     * @return The shopping cart user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the shopping cart user
     *
     * @param user The new shopping cart user
     */
    public void setUser(String user) {
        this.user = user;
    }
}
