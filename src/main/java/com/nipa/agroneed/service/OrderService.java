package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.PlaceOrderDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UpdateOrderDto;

public interface OrderService {
    Response placeOrder(PlaceOrderDto placeOrderDto);
    Response getAllOrders();
    Response updateOrder(UpdateOrderDto updateOrderDto);
}
