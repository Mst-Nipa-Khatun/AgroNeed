package com.nipa.agroneed.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.SelectedProductsDto;
import com.nipa.agroneed.service.ProductsService;
import com.nipa.agroneed.utils.UrlConstraint;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
                RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping(UrlConstraint.Products.ROOT)
public class ProductController {


    private final ProductsService productsService;


    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(path = UrlConstraint.Products.CREATE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response addProducts(@RequestPart("file") MultipartFile file,
                                @RequestParam("data") String selectedProductsDto)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        SelectedProductsDto data = objectMapper.readValue(selectedProductsDto, SelectedProductsDto.class);
        return productsService.addProducts(file, data);
    }
    @GetMapping(UrlConstraint.Products.GET_ALL)
    public Response getAllProducts() {
        return productsService.getAllProducts();
    }

}
