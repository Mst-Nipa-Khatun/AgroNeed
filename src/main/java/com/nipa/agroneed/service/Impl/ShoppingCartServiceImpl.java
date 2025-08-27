package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.config.UserPrincipal;
import com.nipa.agroneed.dto.*;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.entity.ShoppingCartEntity;
import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.repository.ShoppingCartRepository;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.service.ShoppingCartService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductsRepository productsRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductsRepository productsRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public Response createShoppingCart(ShoppingCartDto shoppingCartDto) {
        User user = userRepository.findByIdAndStatus(shoppingCartDto.getUserId(), 1);
        if (user == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "User not found");
        }
        ProductsEntity products = productsRepository.findByIdAndStatus(shoppingCartDto.getProductId(), 1);
        if (products == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Product not found");
        }
        Integer stock = products.getStock();
        if (!(stock != null && stock > 0)) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Stock not enough");
        }
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findByUserIdAndProductIdAndStatus(shoppingCartDto.getUserId(), shoppingCartDto.getProductId(), 1);
        if (shoppingCart != null) {
            return ResponseBuilder.getFailResponse(HttpStatus.CONFLICT, null, "Product already exists," + "you increase or decrease your products");

        }
        shoppingCart = new ShoppingCartEntity();
        shoppingCart.setUserId(user.getId());
        shoppingCart.setStatus(1);
        shoppingCart.setProductId(shoppingCartDto.getProductId());
        shoppingCart.setQuantity(shoppingCartDto.getQuantity());
        ShoppingCartEntity savedCart = shoppingCartRepository.save(shoppingCart);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, savedCart, "Products added in cart");
    }

    @Override
    public Response incrementDecrementShoppingCart(IncrementDecrementShoppingCartDto incrementDecrementShoppingCartDto) {
        User user = userRepository.findByIdAndStatus(incrementDecrementShoppingCartDto.getUserId(), 1);
        if (user == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "User not found");
        }
        ProductsEntity products = productsRepository.findByIdAndStatus(incrementDecrementShoppingCartDto.getProductId(), 1);
        if (products == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Product not found");
        }
        Integer stock = products.getStock();
        if (!(stock != null && stock > 0)) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Stock not enough");
        }
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findByUserIdAndProductIdAndStatus(incrementDecrementShoppingCartDto.getUserId(), incrementDecrementShoppingCartDto.getProductId(), 1);

            if (shoppingCart != null) {
            if (incrementDecrementShoppingCartDto.getIsIncrement()) {
                if (shoppingCart.getQuantity() < stock) {
                    shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
                    ShoppingCartEntity savedCart = shoppingCartRepository.save(shoppingCart);
                    return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedCart, "Products Incremet in cart");
                }
                return ResponseBuilder.getFailResponse(HttpStatus.NOT_ACCEPTABLE, null, "Out of stok");
            } else {
                if (shoppingCart.getQuantity() > 0) {
                    shoppingCart.setQuantity(shoppingCart.getQuantity() - 1);
                    ShoppingCartEntity savedCart = shoppingCartRepository.save(shoppingCart);
                    return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedCart, "Products decrement in cart");

                }
                return ResponseBuilder.getFailResponse(HttpStatus.NOT_ACCEPTABLE, null, "At least 1 product is required");
            }
        }

        return ResponseBuilder.getFailResponse(HttpStatus.CREATED, null, "products and user not found in cart");
    }

    @Override
    public Response getAllShoppingCart(Long userId) {
         List<ShoppingCartProjection> shoppingCartEntities = shoppingCartRepository
                .findAllShoppingCart(1,userId);
        if (!shoppingCartEntities.isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, shoppingCartEntities, "All shopping cart found");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.OK, null, "No shopping cart found");
    }

    @Override
    public Response removeRow(CrossCartDto crossCartDto) {
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findByUserIdAndProductIdAndStatus(crossCartDto.getUserId(), crossCartDto.getProductId(), 1);
        if (shoppingCart != null) {
            shoppingCart.setStatus(3);
            ShoppingCartEntity savedCart = shoppingCartRepository.save(shoppingCart);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, savedCart, "Product removed from cart");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.NOT_FOUND, null, "Product not found");

    }


}
