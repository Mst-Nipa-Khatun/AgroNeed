package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.entity.ProductCommentEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.ProductCommentRepository;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductCommentService {

    private final ProductCommentRepository commentRepository;


    private final ProductsRepository productsRepository;

    private final UserRepository usersRepository;

    public Response addComment(Long productId, Long userId, String commentText) {
        ProductsEntity product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductCommentEntity comment = new ProductCommentEntity();
        comment.setProduct(product);
        comment.setUser(user);
        comment.setComment(commentText);
        comment.setStatus(1);

        commentRepository.save(comment);
        return ResponseBuilder.getFailResponse(HttpStatus.OK,
                comment, "Successfully added comment to product");
    }

    public List<ProductCommentEntity> getCommentsByProductId(Long productId) {
        List<ProductCommentEntity> byProductId = commentRepository.findByProductId(productId);
        Optional<ProductsEntity> byId = productsRepository.findById(productId);
        byProductId.forEach(v->{v.setProduct(byId.get());});
        return byProductId;
    }
}
