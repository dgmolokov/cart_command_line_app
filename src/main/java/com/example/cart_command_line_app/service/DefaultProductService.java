package com.example.cart_command_line_app.service;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService {
  private final ProductRepository repository;

  public Product createProduct(Product product) {
    return repository.createProduct(product);
  }

  public void removeProduct(long productId) {
    repository.removeProduct(productId);
  }

  public Product getProductById(long productId) {
    return repository.getProductById(productId);
  }

  public List<Product> findAll() {
    return repository.findAll();
  }
}
