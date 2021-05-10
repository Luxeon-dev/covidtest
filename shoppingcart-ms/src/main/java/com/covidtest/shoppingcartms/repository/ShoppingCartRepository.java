package com.covidtest.shoppingcartms.repository;

import com.covidtest.shoppingcartms.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository in charge of the requests for the shopping cart database
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findShoppingCartByUser(String username);

    void deleteByUser(String username);
}
