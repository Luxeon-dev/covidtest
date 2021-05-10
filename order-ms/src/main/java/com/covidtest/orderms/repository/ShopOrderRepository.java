package com.covidtest.orderms.repository;

import com.covidtest.orderms.model.ShopOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository in charge of the requests for the shop order database
 */
public interface ShopOrderRepository extends CrudRepository<ShopOrder, Long> {

    /**
     * Find all orders for a given user name
     *
     * @param user The user name
     *
     * @return The list of orders
     */
    Iterable<ShopOrder> findShopOrdersByUser(String user);

    /**
     * Find all orders assigned to a given delivery man
     *
     * @param user The delivery man username
     *
     * @return The list of orders
     */
    Iterable<ShopOrder> findByDeliveryMan(String user);

    /**
     * Find all orders assigned to a given delivery man and with a given delivery status
     *
     * @param user The delivery man username
     * @param delivered The delivery status
     *
     * @return The list of orders
     */
    Iterable<ShopOrder> findByDeliveryManAndDelivered(String user, boolean delivered);
}
