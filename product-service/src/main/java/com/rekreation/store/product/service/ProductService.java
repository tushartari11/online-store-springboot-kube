package com.rekreation.store.product.service;

import com.rekreation.store.product.dto.ProductRequest;
import com.rekreation.store.product.dto.ProductResponse;
import com.rekreation.store.product.model.Product;
import com.rekreation.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product =
                Product.builder()
                        .name(productRequest.name())
                        .description(productRequest.description())
                        .price(productRequest.price())
                        .skuCode(productRequest.skuCode())
                        .build();
        productRepository.save(product);
        log.info("Product Created Successfully");
        return new ProductResponse(
                product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(
                        product ->
                                new ProductResponse(
                                        product.getId(),
                                        product.getName(),
                                        product.getDescription(),
                                        product.getSkuCode(),
                                        product.getPrice()))
                .toList();
    }
}
