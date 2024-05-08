package com.productshop.simpleshop.repository;

import com.productshop.simpleshop.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
