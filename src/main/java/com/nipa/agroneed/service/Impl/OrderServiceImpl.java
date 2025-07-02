package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.PlaceOrderDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UpdateOrderDto;
import com.nipa.agroneed.entity.OrderItemsEntity;
import com.nipa.agroneed.entity.OrdersEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.entity.ShoppingCartEntity;
import com.nipa.agroneed.repository.OrderItemsRepository;
import com.nipa.agroneed.repository.OrderRepository;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.repository.ShoppingCartRepository;
import com.nipa.agroneed.service.OrderService;
import com.nipa.agroneed.utils.ResponseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductsRepository productsRepository;
    private final OrderItemsRepository orderItemsRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, ProductsRepository productsRepository, OrderItemsRepository orderItemsRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productsRepository = productsRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    @Transactional
    @Override
    public Response placeOrder(PlaceOrderDto placeOrderDto) {
        List<ShoppingCartEntity> carts = shoppingCartRepository.findAllByUserIdAndStatus(placeOrderDto.getUserId(), 1);

        if (carts.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No items in cart for this user.");
        }

        double totalPrice = 0.0;
        int totalNumberOfProducts = 0;

        for (ShoppingCartEntity cart : carts) {
            ProductsEntity product = productsRepository.findByIdAndStatus(cart.getProductId(), 1);
            if (product != null) {
                if (product.getStock() < cart.getQuantity()) {
                    return ResponseBuilder.getFailResponse(HttpStatus.NOT_ACCEPTABLE,
                            null,product.getName() +" is out of stock. " +
                                    "For continue this order you can remove the product then place order");
                }
                totalPrice += product.getPrice() * cart.getQuantity();
                totalNumberOfProducts++;
                //decrease the e-commerce company product count as we received the order
                product.setStock(product.getStock() - cart.getQuantity());
                productsRepository.save(product);
            }
            cart.setStatus(4);//For make it received in shopping cart
        }
        List<ShoppingCartEntity> updatedCarts = shoppingCartRepository.saveAll(carts); //akhon shooping cart e pick kora gulo 4 status

        OrdersEntity order = new OrdersEntity();
        order.setUserId(placeOrderDto.getUserId());
        order.setAddress(placeOrderDto.getAddress());
        order.setPhoneNumber(placeOrderDto.getPhoneNumber());
        order.setPaymentMethod(placeOrderDto.getPaymentMethod());
        order.setStatus(1);
        order.setTotalPrice(totalPrice);
        order.setNumberOfProducts(totalNumberOfProducts);
        OrdersEntity savedOrder = orderRepository.save(order);

        List<OrderItemsEntity> orderItems = new ArrayList<>();
        for (ShoppingCartEntity shoppingCartart : updatedCarts) {
            ProductsEntity product = productsRepository.findByIdAndStatus(shoppingCartart.getProductId(), 1);
            if (product != null) {
                OrderItemsEntity orderItem = new OrderItemsEntity();
                orderItem.setOrderId(savedOrder.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(shoppingCartart.getQuantity());
                orderItem.setStatus(1);
                orderItems.add(orderItem);
            }
        }
        orderItemsRepository.saveAll(orderItems);

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedOrder, "Order placed successfully.");
    }

    @Override
    public Response getAllOrders() {
        List<OrdersEntity> orders=orderRepository.findAll();
        if (!orders.isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, orders, "Orders found.");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No orders found.");
    }

    @Transactional
    @Override
    public Response updateOrder(UpdateOrderDto updateOrderDto) {
        Optional<OrdersEntity> ordersOptional=orderRepository.findById(updateOrderDto.getOrderId());
        if (!ordersOptional.isPresent()) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No orders found.");
        }
        OrdersEntity orders = ordersOptional.get();
        List<OrderItemsEntity> orderItems=
                orderItemsRepository.findByOrderId(orders.getId());

        if (orderItems.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No order items found.");
        }

        orders.setStatus(updateOrderDto.getOrderStatus());
        OrdersEntity savedOrder=orderRepository.save(orders);

        List<OrderItemsEntity> orderItemsEntities=new ArrayList<>();
        for (OrderItemsEntity orderItem : orderItems) {
            orderItem.setStatus(updateOrderDto.getOrderStatus());
            orderItemsEntities.add(orderItem);
        }
        orderItemsRepository.saveAll(orderItemsEntities);

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedOrder, "Order updated successfully.");
    }

}
