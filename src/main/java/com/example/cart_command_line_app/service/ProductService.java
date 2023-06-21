package com.example.cart_command_line_app.service;

import com.example.cart_command_line_app.jpa.Product;

import java.util.List;

public interface ProductService {
  Product createProduct(Product product);
  void removeProduct(long productId);
  Product getProductById(long productId);
  List<Product> findAll();
}
