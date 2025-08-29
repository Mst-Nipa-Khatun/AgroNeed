package com.nipa.agroneed.SSLCommerz.controller;

import com.nipa.agroneed.SSLCommerz.dto.PaymentRequest;
import com.nipa.agroneed.SSLCommerz.dto.PaymentResponse;
import com.nipa.agroneed.SSLCommerz.dto.response.InitiatePaymentResponse;
import com.nipa.agroneed.SSLCommerz.service.PaymentService;
import com.nipa.agroneed.entity.OrderItemsEntity;
import com.nipa.agroneed.entity.OrdersEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.entity.ShoppingCartEntity;
import com.nipa.agroneed.repository.OrderItemsRepository;
import com.nipa.agroneed.repository.OrderRepository;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
                RequestMethod.DELETE, RequestMethod.OPTIONS})
@Controller
@RequestMapping("/ssl-commerz")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductsRepository productsRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    /*
        @GetMapping("/") //@INFO This is just for example
         public String home() {
             return "SSLCommerz/index";
         }
     */
    @PostMapping("/initiate-payment")/*@INFO JUST FOR DEMO PRACTICE {NO USE IN THIS PROJECT}*/
    @ResponseBody
    public PaymentResponse initiatePayment(@RequestBody PaymentRequest request) {
        try {
            InitiatePaymentResponse response = paymentService.initiatePayment(request, generateTransactionId());

            if (response.getSessionkey() != null && !response.getSessionkey().isEmpty()) {
                return new PaymentResponse(true, "Payment initiated successfully", response);
            } else {
                return new PaymentResponse(false, "SSLCommerz did not return sessionkey", null);
            }

        } catch (Exception e) {
            return new PaymentResponse(false, "Payment initiation failed: " + e.getMessage(), null);
        }
    }

    @PostMapping("/payment/success")
    public String paymentSuccess(@RequestParam Map<String, String> params, Model model) {
        // Example fields SSLCommerz sends
        String tranId = params.get("tran_id");          // Transaction ID
        String amount = params.get("amount");           // Paid amount
        String cardType = params.get("card_type");      // Card/Mobile Banking type
        String bankTranId = params.get("bank_tran_id"); // Bank Transaction ID

        // Add data to the view
        model.addAttribute("tranId", tranId);
        model.addAttribute("amount", amount);
        model.addAttribute("cardType", cardType);
        model.addAttribute("bankTranId", bankTranId);

        // TODO: Save transaction details into DB

        OrdersEntity order = orderRepository.findByTransactionIdAndStatus(tranId, 11);
        order.setStatus(1);
        order.setTransactionStatus(1);
        order.setBankTransactionId(bankTranId);
        orderRepository.save(order);

        List<OrderItemsEntity> orderItemsEntities = orderItemsRepository.findByOrderId(order.getId());
        orderItemsEntities.forEach(v -> {
            v.setStatus(1);
            orderItemsRepository.save(v);
        });


        List<ShoppingCartEntity> allShoppingCart =
                shoppingCartRepository.findAllByUserIdAndStatus(order.getUserId(), 11);
        allShoppingCart.forEach(cart->{
            cart.setStatus(2);
            shoppingCartRepository.save(cart);
        });


        return "SSLCommerz/success";  // success.html view will show details
    }


    @PostMapping("/payment/fail")
    public String paymentFail(@RequestParam Map<String, String> params, Model model) {
        // Example fields for failed transactions
        String tranId = params.get("tran_id");               // Transaction ID
        String errorReason = params.get("error");            // Failure reason (if available)
        String failedReason = params.get("failedreason");    // SSLCommerz failure reason

        model.addAttribute("tranId", tranId);
        model.addAttribute("failedReason", failedReason != null ? failedReason : errorReason);

        // TODO: log/store failed transaction in DB
        OrdersEntity order = orderRepository.findByTransactionIdAndStatus(tranId, 11);
        order.setStatus(3);
        order.setTransactionStatus(3);
        order.setFailedTransactionReason(errorReason.concat("__") + failedReason);
        //order.setBankTransactionId(bankTranId);
        orderRepository.save(order);

        List<OrderItemsEntity> orderItemsEntities = orderItemsRepository.findByOrderId(order.getId());
        orderItemsEntities.forEach(v -> {
            v.setStatus(3);
            orderItemsRepository.save(v);

            /*INCREASE THE PRODUCT QUANTITY*/
            ProductsEntity product = productsRepository.findById(v.getProductId()).get();
            product.setStock((product.getStock() == null ? 0 : product.getStock()) + v.getQuantity());
            productsRepository.save(product);
        });


        List<ShoppingCartEntity> allShoppingCart =
                shoppingCartRepository.findAllByUserIdAndStatus(order.getUserId(), 11);
        allShoppingCart.forEach(cart->{
            cart.setStatus(1);
            shoppingCartRepository.save(cart);
        });



        return "SSLCommerz/fail"; // fail.html view will show failure reason
    }

    @PostMapping("/payment/cancel")
    public String paymentCancel(@RequestParam Map<String, String> params, Model model) {
        String tranId = params.get("tran_id");
        model.addAttribute("tranId", tranId);


        OrdersEntity order = orderRepository.findByTransactionIdAndStatus(tranId, 11);
        order.setStatus(33);
        order.setTransactionStatus(33);
        order.setFailedTransactionReason("User cancelled transaction");
        //order.setBankTransactionId(bankTranId);
        orderRepository.save(order);

        List<OrderItemsEntity> orderItemsEntities = orderItemsRepository.findByOrderId(order.getId());
        orderItemsEntities.forEach(v -> {
            v.setStatus(33);
            orderItemsRepository.save(v);

            /*INCREASE THE PRODUCT QUANTITY*/
            ProductsEntity product = productsRepository.findById(v.getProductId()).get();
            product.setStock((product.getStock() == null ? 0 : product.getStock()) + v.getQuantity());
            productsRepository.save(product);
        });

        List<ShoppingCartEntity> allShoppingCart =
                shoppingCartRepository.findAllByUserIdAndStatus(order.getUserId(), 11);
        allShoppingCart.forEach(cart->{
            cart.setStatus(1);
            shoppingCartRepository.save(cart);
        });

        return "SSLCommerz/cancel";
    }

    @PostMapping("/payment/ipn")
    @ResponseBody
    public String paymentIPN(@RequestParam Map<String, String> params) {
        // TODO: validate with SSLCommerz API and update DB
        return "IPN received";
    }

    private String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
