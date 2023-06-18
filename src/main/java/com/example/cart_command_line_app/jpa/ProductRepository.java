package com.example.cart_command_line_app.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class ProductRepository {
  private final List<Product> products;

  public void createProduct(Product product) {
    products.add(product);
  }

  public Product getProductById(Long productId) throws IllegalArgumentException {
    return products.stream().filter(product -> productId.
      equals(product.getId())).
      findAny().
      orElseThrow(() -> new IllegalArgumentException("Object not found!"));
  }
}
