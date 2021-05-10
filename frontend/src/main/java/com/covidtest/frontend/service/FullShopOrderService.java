package com.covidtest.frontend.service;

import com.covidtest.frontend.feign.ProductMsFeignClient;
import com.covidtest.frontend.model.FullShopOrder;
import com.covidtest.frontend.model.Product;
import com.covidtest.frontend.model.ShopOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FullShopOrderService {

    @Autowired
    private ProductMsFeignClient productMsFeignClient;

    public List<FullShopOrder> completeOrdersWithFullProductData(Iterable<ShopOrder> orders, String token) {
        List<FullShopOrder> fullOrders = new ArrayList<>();
        for (ShopOrder order: orders) {
            FullShopOrder fullShopOrder = new FullShopOrder(order);
            fullOrders.add(fullShopOrder);

            for (int i = 0; i < order.getOrderEntries().size(); i++) {
                Long productId = order.getOrderEntries().get(i).getProductId();
                Product product;
                try {
                    product = productMsFeignClient.getProductById(token, productId);
                }
                catch (Exception e) {
                    product = new Product();
                    product.setName("Removed product");
                }
                fullShopOrder.getOrderEntries().get(i).setProduct(product);
            }
        }

        return fullOrders;
    }
}
