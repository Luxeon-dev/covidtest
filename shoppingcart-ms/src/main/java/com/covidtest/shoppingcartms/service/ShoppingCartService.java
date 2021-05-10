package com.covidtest.shoppingcartms.service;

import com.covidtest.shoppingcartms.model.ShoppingCart;
import com.covidtest.shoppingcartms.model.ShoppingCartEntry;
import com.covidtest.shoppingcartms.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service which handle ShoppingCart CRUD
 */
@Service
public class ShoppingCartService {

    /**
     * ShoppingCart repository to handle database requests
     */
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    /**
     * Get user's shopping cart
     *
     * @param user The currently connected user name
     *
     * @return The user's shopping cart if exists (i.e. if the user has added at least one product)
     */
    public Optional<ShoppingCart> getCart(String user) {
        return shoppingCartRepository.findShoppingCartByUser(user);
    }

    /**
     * Add Shopping cart entry for a specific product to the user's cart
     *
     * @param user The currently connected user name
     * @param entry The new shopping cart entry to add (product + quantity)
     *
     * @return The new user's shopping cart
     */
    public ShoppingCart setShoppingCart(String user, ShoppingCartEntry entry) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findShoppingCartByUser(user);

        ShoppingCart cart;

        if (optionalCart.isEmpty()) { // Si il n'y a pas de ShoppingCart pour ce user, on en crée un nouveau avec la nouvelle ligne du produit
            cart = new ShoppingCart();
            cart.setUser(user);
            cart.setEntries(Arrays.asList(entry));
        }
        else { // Si il y en a déjà un, on le met à jour
            cart = optionalCart.get();
            List<ShoppingCartEntry> entries = cart.getEntries();

            // On test si il n'y a pas déjà le produit à ajouter
            ShoppingCartEntry existingEntry = entries.stream().filter(e -> entry.getProductId().equals(e.getProductId())).findFirst().orElse(null);

            if (existingEntry == null) { // Si non, on ajoute la ligne
                entries.add(entry);
            }
            else { // Si oui, on ajoute juste la quantité à la ligne existante
                int newQuantity = existingEntry.getQuantity() + entry.getQuantity();
                existingEntry.setQuantity(newQuantity);
            }

            cart.setEntries(entries);
        }

        cart = shoppingCartRepository.save(cart);

        return cart;
    }

    /**
     * Delete the user's shopping cart
     *
     * @param user The currently connected user name
     */
    public void deleteCart(String user) {
        shoppingCartRepository.deleteByUser(user);
    }
}
