package com.covidtest.orderms.controller;

import com.covidtest.orderms.dto.ShopOrderDto;
import com.covidtest.orderms.dto.UnidentifiedShopOrderDto;
import com.covidtest.orderms.model.ShopOrder;
import com.covidtest.orderms.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * RestController
 * Handles all the CRUD requests for ShopOrder entity
 */
@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    /**
     * Service used to request the ShopOrder table
     */
    @Autowired
    private OrderService orderService;

    /**
     * Model mapper for conversion between Entity and Dto
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all orders
     *
     * @return The list of orders
     */
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopOrderDto> getAllOrders() {
        Iterable<ShopOrder> orders = orderService.getOrders();

        return toListOfShopOrderDto(orders);
    }

    /**
     * Get all orders of a specific customer
     *
     * @param principal The currently connected customer
     *
     * @return The list of orders
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopOrderDto> getOrdersByUser(Principal principal) {
        Iterable<ShopOrder> orders = orderService.getOrdersByUser(principal.getName());

        return toListOfShopOrderDto(orders);
    }

    /**
     * Get all orders which are not already assigned to a delivery man for the delivery
     *
     * @return The list of orders
     */
    @GetMapping(path = "/unassigned", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopOrderDto> getUnassignedOrders() {
        Iterable<ShopOrder> orders = orderService.getOrdersWithoutDeliveryMan();

        return toListOfShopOrderDto(orders);
    }

    /**
     * Get all orders assigned to a specific delivery man but not delivered for the moment
     *
     * @param principal The currently connected delivery man
     *
     * @return The list of orders
     */
    @GetMapping(path = "/assigned", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopOrderDto> getOrdersToDeliver(Principal principal) {
        Iterable<ShopOrder> orders = orderService.getUndeliveredOrdersByDeliveryMan(principal.getName());

        return toListOfShopOrderDto(orders);
    }

    /**
     * Submit a new order
     *
     * @param unidentifiedShopOrderDto The order to register
     * @param principal The currently connected customer
     *
     * @return The registered order
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShopOrderDto postOrder(@RequestBody UnidentifiedShopOrderDto unidentifiedShopOrderDto, Principal principal) {
        ShopOrder order = toEntity(unidentifiedShopOrderDto);
        ShopOrder createdOrder = orderService.createOrder(order, principal.getName());

        return toDto(createdOrder);
    }

    /**
     * Assign a delivery man to an order
     *
     * @param id Id of the order
     * @param principal The currently connected delivery man
     *
     * @return The updated order
     */
    @PutMapping(path = "/{id}/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShopOrderDto assign(@PathVariable Long id, Principal principal) {
        ShopOrder order = orderService.updateOrderDeliveryMan(id, principal.getName());

        return toDto(order);
    }

    /**
     * Set a order as delivered
     *
     * @param id Id of the order
     *
     * @return The updated order
     */
    @PutMapping(path = "/{id}/delivered", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShopOrderDto markAsDelivered(@PathVariable Long id) {
        ShopOrder order = orderService.updateOrderDelivered(id, true);

        return toDto(order);
    }

    /**
     * Convert a ShopOrder entity to an identified ShopOrder Dto
     *
     * @param order The ShopOrder entity to convert
     *
     * @return The identified ShopOrder Dto corresponding
     */
    private ShopOrderDto toDto(ShopOrder order) {
        return modelMapper.map(order, ShopOrderDto.class);
    }

    /**
     * Convert an unidentified ShopOrder Dto to a ShopOrder entity
     *
     * @param unidentifiedShopOrderDto The ShopOrder Dto to convert
     *
     * @return The ShopOrder entity corresponding
     */
    private ShopOrder toEntity(UnidentifiedShopOrderDto unidentifiedShopOrderDto) {
        return modelMapper.map(unidentifiedShopOrderDto, ShopOrder.class);
    }

    /**
     * Convert an Iterable of ShopOrder into a List of ShopOrder Dto
     *
     * @param orders The iterable of ShopOrder
     *
     * @return The list of ShopOrder Dto
     */
    private List<ShopOrderDto> toListOfShopOrderDto(Iterable<ShopOrder> orders) {
        List<ShopOrderDto> shopOrderDtos = new ArrayList<>();
        orders.forEach(o -> shopOrderDtos.add(toDto(o)));

        return shopOrderDtos;
    }
}
