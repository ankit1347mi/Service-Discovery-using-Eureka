package com.tyss.ProductService.service;

import com.tyss.ProductService.dto.ProductRequest;
import com.tyss.ProductService.dto.ProductResponse;
import com.tyss.ProductService.model.Product;
import com.tyss.ProductService.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {


    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepo.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepo.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();
    }
}
