package com.rekreation.store.product.controller;

import com.rekreation.store.product.dto.ProductRequest;
import com.rekreation.store.product.dto.ProductResponse;
import com.rekreation.store.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/health")
  public String healthCheck() {
    return "Running fine!";
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
    return productService.createProduct(productRequest);
  }
}
