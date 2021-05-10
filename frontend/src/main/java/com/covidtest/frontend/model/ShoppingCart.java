package com.covidtest.frontend.model;

import java.util.List;

public class ShoppingCart {

    private Long id;

    private List<ShoppingCartEntry> entries;

    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ShoppingCartEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ShoppingCartEntry> entries) {
        this.entries = entries;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
