package com.covidtest.shoppingcartms.controller;

import com.covidtest.shoppingcartms.dto.ShoppingCartDto;
import com.covidtest.shoppingcartms.dto.UnidentifiedShoppingCartEntryDto;
import com.covidtest.shoppingcartms.model.ShoppingCart;
import com.covidtest.shoppingcartms.model.ShoppingCartEntry;
import com.covidtest.shoppingcartms.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * RestController
 * Handles CRUD request for the user ShoppingCart
 */
@RestController
@RequestMapping(path = "/api/v1/cart")
public class ShoppingCartController {

    /**
     * Service used to request the shopping cart database
     */
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Model mapper for conversion between Entity and Dto
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get the shopping car of the connected user
     *
     * @param principal The currently connected user
     *
     * @return The shopping cart of the user or null if he doesn't already have a cart (i.e. no product in the cart)
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCartDto getShoppingCart(Principal principal) {
        Optional<ShoppingCart> shoppingCart = shoppingCartService.getCart(principal.getName());

        return shoppingCart.isEmpty() ? null : shoppingCartToDto(shoppingCart.get());
    }

    /**
     * Add a product with its quantity to the user's cart
     *
     * @param shoppingCartEntryDto The shopping cart line to add (product + quantity)
     * @param principal The currently connected user
     *
     * @return The new shopping cart
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCartDto addProductToCart(@RequestBody UnidentifiedShoppingCartEntryDto shoppingCartEntryDto, Principal principal) {
        ShoppingCartEntry shoppingCartEntry = shoppingCartEntryDtoToEntity(shoppingCartEntryDto);
        ShoppingCart shoppingCart = shoppingCartService.setShoppingCart(principal.getName(), shoppingCartEntry);

        return shoppingCartToDto(shoppingCart);
    }

    /**
     * Delete the user's shopping cart
     *
     * @param principal The currently connected user
     */
    @Transactional
    @DeleteMapping
    public void deleteShoppingCart(Principal principal) {
        shoppingCartService.deleteCart(principal.getName());
    }

    /**
     * Convert a ShoppingCart entity to an identified ShoppingCart Dto
     *
     * @param shoppingCart The ShoppingCart entity to convert
     *
     * @return The identified ShoppingCart Dto corresponding
     */
    private ShoppingCartDto shoppingCartToDto(ShoppingCart shoppingCart) {
        return modelMapper.map(shoppingCart, ShoppingCartDto.class);
    }

    /**
     * Convert an unidentified ShoppingCartEntry Dto to a ShoppingCartEntry entity
     *
     * @param unidentifiedShoppingCartEntryDto The ShoppingCartEntry Dto to convert
     *
     * @return The ShoppingCartEntry entity corresponding
     */
    private ShoppingCartEntry shoppingCartEntryDtoToEntity(UnidentifiedShoppingCartEntryDto unidentifiedShoppingCartEntryDto) {
        return modelMapper.map(unidentifiedShoppingCartEntryDto, ShoppingCartEntry.class);
    }
}
