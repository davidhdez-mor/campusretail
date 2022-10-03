package com.campusretail.orderservice.utilities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.campusretail.orderservice.domain.*;

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
    
   public static Order createOrder(Cart cart, List<Item> items, User user) {
        Order order = new Order();
        order.setCart(cart);
        order.setItems(items);
        order.setUser(user);
        order.setTotal(OrderUtilities.countTotalPrice(cart.getItems()));
        order.setOrderedDate(LocalDate.now());
        order.setStatus(Status.OPEN);
        return order;
       }
}
