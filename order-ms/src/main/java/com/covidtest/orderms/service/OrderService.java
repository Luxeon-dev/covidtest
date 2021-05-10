package com.covidtest.orderms.service;

import com.covidtest.orderms.model.ShopOrder;
import com.covidtest.orderms.repository.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

/**
 * Service which handle ShopOrder CRUD
 */
@Service
public class OrderService {

    /**
     * ShopOrder repository to handle database requests
     */
    @Autowired
    private ShopOrderRepository orderRepository;

    /**
     * Get all orders
     *
     * @return All database orders
     */
    public Iterable<ShopOrder> getOrders() {
        return orderRepository.findAll();
    }

    /**
     * Get all orders of a user
     *
     * @param username The user name
     *
     * @return The list of orders
     */
    public Iterable<ShopOrder> getOrdersByUser(String username) {
        return orderRepository.findShopOrdersByUser(username);
    }

    /**
     * Get all orders without an assigned delivery man
     *
     * @return The list of orders
     */
    public Iterable<ShopOrder> getOrdersWithoutDeliveryMan() {
        return orderRepository.findByDeliveryMan(null);
    }

    /**
     * Get all undelivered orders assigned to a specific delivery man
     *
     * @param username The delivery man username
     *
     * @return The list of orders
     */
    public Iterable<ShopOrder> getUndeliveredOrdersByDeliveryMan(String username) {
        return orderRepository.findByDeliveryManAndDelivered(username, false);
    }

    /**
     * Create an order
     *
     * @param order The order to register
     * @param username The order user name
     *
     * @return The registered order
     */
    public ShopOrder createOrder(ShopOrder order, String username) {
        order.setUser(username);
        order.setOrderedAt(new Date());

        return orderRepository.save(order);
    }

    /**
     * Set the delivery man of an order
     *
     * @param id The order id
     * @param username The delivery man username
     *
     * @return The updated order
     */
    public ShopOrder updateOrderDeliveryMan(Long id, String username) {
        ShopOrder order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        order.setDeliveryMan(username);

        return orderRepository.save(order);
    }

    /**
     * Set the delivery status of an order
     *
     * @param id The order id
     * @param status The new status of the order
     *
     * @return The updated order
     */
    public ShopOrder updateOrderDelivered(Long id, Boolean status) {
        ShopOrder order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        order.setDelivered(status);

        return orderRepository.save(order);
    }
}
