package com.campusretail.orderservice.utilities;

import java.math.BigDecimal;

import com.campusretail.orderservice.domain.Product;

/**
 * Class with useful methods
 * for the Cart class
 */
public class CartUtilities {

    public static BigDecimal getSubTotalForItem(Product product, int quantity){
       return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
