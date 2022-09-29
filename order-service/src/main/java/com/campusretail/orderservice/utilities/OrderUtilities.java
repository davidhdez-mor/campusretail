package com.campusretail.orderservice.utilities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Order;
import com.campusretail.orderservice.domain.User;

/**
 * Class with useful methods
 * for the Order class
 */
public class OrderUtilities {

    public static BigDecimal countTotalPrice(List<Item> cart){
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : cart) {
            total = total.add(item.getSubTotal());
        }
        return total;
    }
    
   public static Order createOrder(List<Item> cart, User user) {
        Order order = new Order();
        order.setItems(cart);
        order.setUser(user);
        order.setTotal(OrderUtilities.countTotalPrice(cart));
        order.setOrderedDate(LocalDate.now());
        order.setStatus("PAYMENT_EXPECTED");
        return order;
    }
}
