package com.nipa.agroneed.dto;

public record ProductCommentRequest(
        Long productId,
        Long userId,
        String comment
) {}
