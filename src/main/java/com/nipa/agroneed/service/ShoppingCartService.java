package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.CrossCartDto;
import com.nipa.agroneed.dto.IncrementDecrementShoppingCartDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.ShoppingCartDto;

public interface ShoppingCartService {
    Response createShoppingCart(ShoppingCartDto shoppingCartDto);
    Response incrementDecrementShoppingCart(IncrementDecrementShoppingCartDto incrementDecrementShoppingCartDto);
    Response getAllShoppingCart();
    Response removeRow(CrossCartDto crossCartDto);
    //Response removeRow(Long productId);
}
