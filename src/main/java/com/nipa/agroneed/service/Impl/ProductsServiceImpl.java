package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SelectedProductsDto;
import com.nipa.agroneed.dto.ViewProductsDetailsProjection;
import com.nipa.agroneed.entity.CategoriesEntity;
import com.nipa.agroneed.entity.ProductCategoriesEntity;
import com.nipa.agroneed.entity.ProductsEntity;
import com.nipa.agroneed.repository.CategoriesRepository;
import com.nipa.agroneed.repository.ProductsCategoriesRepository;
import com.nipa.agroneed.repository.ProductsRepository;
import com.nipa.agroneed.service.ProductsService;
import com.nipa.agroneed.utils.ResponseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductsCategoriesRepository productsCategoriesRepository;


    @Value("${file.upload-dir}")
    private String imageStoreLocation;

    public ProductsServiceImpl(ProductsRepository productsRepository, CategoriesRepository categoriesRepository, ProductsCategoriesRepository productsCategoriesRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.productsCategoriesRepository = productsCategoriesRepository;
    }

    @Transactional
    @Override
    public Response addProducts(MultipartFile file, SelectedProductsDto selectedProductsDto) throws IOException {
        String directoryPath = new File(imageStoreLocation).getAbsolutePath();
        File dir = new File(directoryPath); //system er file hisebe create holo

        // Create directory if not exists
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Define file path
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename()
                .replace(" ", "");
        String filePath = directoryPath + "/" + fileName;
        Path path = Paths.get(filePath);

        // Save file to disk
        Files.write(path, file.getBytes()); //automatic file e save kore dey


        CategoriesEntity categories = categoriesRepository.findByIdAndStatus(selectedProductsDto.getSelectedCategoryId(), 1);
        if (categories == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Categories Id not found");
        }

        List<CategoriesEntity> categoriesEntities =
                categoriesRepository.findByParentIdAndStatus(selectedProductsDto.getSelectedCategoryId(), 1);

        if (!categoriesEntities.isEmpty()) {
            return ResponseBuilder.getFailResponse(HttpStatus.CONFLICT, null,
                    "This selectedCategoryId have a sub category ,you don't add any products");

        }
        ProductsEntity products = productsRepository.findByNameAndStatus(selectedProductsDto.getName(), 1);
        if (products == null) {
            products = new ProductsEntity();
            products.setName(selectedProductsDto.getName());
            products.setDescription(selectedProductsDto.getDescription());
            products.setPrice(selectedProductsDto.getPrice());
            products.setStatus(1);
            products.setStock(selectedProductsDto.getStock());
            products.setImageUrl("/images/" + fileName);

            ProductsEntity savedProducts = productsRepository.save(products);


            ProductCategoriesEntity productCategoriesEntity = new ProductCategoriesEntity();
            productCategoriesEntity.setProductId(savedProducts.getId());
            productCategoriesEntity.setCategoryId(categories.getId());
            productCategoriesEntity.setStatus(1);
            productsCategoriesRepository.save(productCategoriesEntity);

            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null,
                    "Successfully added products");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Product already exists");
    }

    @Override
    public Response getAllProducts() {
        List<ViewProductsDetailsProjection> productsList = productsRepository.findByAllProducts();
        if (!productsList.isEmpty()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, productsList, "Successfully retrieved products");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "No products found");
    }
}
