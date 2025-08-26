package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SelectedProductsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductsService {
    Response addProducts(MultipartFile file, SelectedProductsDto selectedProductsDto) throws IOException;
    Response getAllProducts();
    Response getProductsBySupplierId(Long supplierId);
}
