package com.nipa.agroneed.controller.api;


import com.nipa.agroneed.dto.PlaceOrderDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UpdateOrderDto;
import com.nipa.agroneed.service.OrderService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstraint.Orders.ROOT)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

   @PostMapping(UrlConstraint.Orders.PLACE_ORDER)
    public Response placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return orderService.placeOrder(placeOrderDto);
   }
   @GetMapping(UrlConstraint.Orders.GET_ALL)
    public Response getAllOrders() {
        return orderService.getAllOrders();
   }
   @PostMapping(UrlConstraint.Orders.UPADATE_ORDER)
    public Response updateOrder(@RequestBody UpdateOrderDto updateOrderDto){
        return orderService.updateOrder(updateOrderDto);
   }
}
