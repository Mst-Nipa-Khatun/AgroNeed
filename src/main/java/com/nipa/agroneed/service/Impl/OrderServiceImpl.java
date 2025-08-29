package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.SSLCommerz.dto.PaymentRequest;
import com.nipa.agroneed.SSLCommerz.dto.PaymentResponse;
import com.nipa.agroneed.SSLCommerz.dto.response.InitiatePaymentResponse;
import com.nipa.agroneed.SSLCommerz.service.PaymentService;
import com.nipa.agroneed.dto.PlaceOrderDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UpdateOrderDto;
import com.nipa.agroneed.entity.*;
import com.nipa.agroneed.repository.*;
import com.nipa.agroneed.service.OrderService;
import com.nipa.agroneed.utils.ResponseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductsRepository productsRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, ProductsRepository productsRepository, OrderItemsRepository orderItemsRepository, PaymentService paymentService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productsRepository = productsRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.paymentService = paymentService;
        this.userRepository = userRepository;
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

        List<String> productNames = new ArrayList<>();
        for (ShoppingCartEntity cart : carts) {
            ProductsEntity product = productsRepository.findByIdAndStatus(cart.getProductId(), 1);
            if (product != null) {
                if (product.getStock() < cart.getQuantity()) {
                    return ResponseBuilder.getFailResponse(HttpStatus.NOT_ACCEPTABLE,
                            null, product.getName() + " is out of stock. " +
                                    "For continue this order you can remove the product then place order");
                }
                productNames.add(product.getName());
                totalPrice += product.getPrice() * cart.getQuantity();
                totalNumberOfProducts++;
                //decrease the e-commerce company product count as we received the order
                product.setStock(product.getStock() - cart.getQuantity());
                productsRepository.save(product);
            }
            cart.setStatus(11);//For make, it received in shopping cart
        }
        List<ShoppingCartEntity> updatedCarts = shoppingCartRepository.saveAll(carts); //akhon shooping cart e pick kora gulo 4 status

        OrdersEntity order = new OrdersEntity();
        order.setUserId(placeOrderDto.getUserId());
        order.setAddress(placeOrderDto.getAddress());
        order.setPhoneNumber(placeOrderDto.getPhoneNumber());
        order.setPaymentMethod(placeOrderDto.getPaymentMethod());
        order.setStatus(11);
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
                orderItem.setStatus(11);
                orderItems.add(orderItem);
            }
        }
        orderItemsRepository.saveAll(orderItems);


        /*PAYMENT REQUEST*/
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(String.valueOf(totalPrice));
        User user = userRepository.findByIdAndStatus(placeOrderDto.getUserId(), 1);
        paymentRequest.setCustomerName(user.getName());
        paymentRequest.setCustomerEmail(user.getEmail());
        paymentRequest.setCustomerAddress1((user.getAddress() == null ? "N/A" : user.getAddress()));
        paymentRequest.setCustomerCity(user.getAddress() == null ? "N/A" : user.getAddress());
        paymentRequest.setCustomerPostcode("N/A");
        paymentRequest.setCustomerCountry("Bangladesh");
        paymentRequest.setCustomerPhone(user.getPhone());
        paymentRequest.setProductName(String.join(",", productNames));
        paymentRequest.setProductCategory("E-commerce");

        String trxId = generateTransactionId();

        savedOrder.setTransactionId(trxId);
        savedOrder.setTransactionStatus(11);
        orderRepository.save(savedOrder);


        InitiatePaymentResponse response = paymentService.initiatePayment(paymentRequest, trxId);

        if (response.getSessionkey() != null && !response.getSessionkey().isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    response, "Order placed and Payment initiated successfully, Now Pending For Payment");
        } else {
            return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "Order placed but SSLCommerz did not return sessionkey");
        }

    }

    private String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    @Override
    public Response getAllOrders() {
        List<OrdersEntity> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, orders, "Orders found.");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No orders found.");
    }

    @Transactional
    @Override
    public Response updateOrder(UpdateOrderDto updateOrderDto) {
        Optional<OrdersEntity> ordersOptional = orderRepository.findById(updateOrderDto.getOrderId());
        if (!ordersOptional.isPresent()) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No orders found.");
        }
        OrdersEntity orders = ordersOptional.get();
        List<OrderItemsEntity> orderItems =
                orderItemsRepository.findByOrderId(orders.getId());

        if (orderItems.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No order items found.");
        }

        orders.setStatus(updateOrderDto.getOrderStatus());
        OrdersEntity savedOrder = orderRepository.save(orders);

        List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();
        for (OrderItemsEntity orderItem : orderItems) {
            orderItem.setStatus(updateOrderDto.getOrderStatus());
            orderItemsEntities.add(orderItem);
        }
        orderItemsRepository.saveAll(orderItemsEntities);

        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedOrder, "Order updated successfully.");
    }

}
