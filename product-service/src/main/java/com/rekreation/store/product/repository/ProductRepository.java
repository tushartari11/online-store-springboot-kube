package com.rekreation.store.product.repository;


import com.rekreation.store.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}
