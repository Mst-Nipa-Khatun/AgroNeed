package com.nipa.agroneed.controller.api;

import com.nipa.agroneed.dto.ProductCommentRequest;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.entity.ProductCommentEntity;
import com.nipa.agroneed.service.ProductCommentService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class ProductCommentController {

    @Autowired
    private ProductCommentService commentService;

    // Add comment
    @PostMapping("/add")
    public Response addComment(
            @RequestBody ProductCommentRequest request
    ) {
        return commentService.addComment(request.productId(), request.userId(), request.comment());

    }

    @GetMapping("/product/{productId}")
    public Response getCommentsByProductId(
            @PathVariable Long productId
    ) {
        List<ProductCommentEntity> comments = commentService.getCommentsByProductId(productId);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, comments,
                "Successfully retrieved comments");
    }
}
