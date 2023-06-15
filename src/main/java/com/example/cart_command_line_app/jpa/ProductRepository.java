package com.example.cart_command_line_app.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
@Getter
public class ProductRepository {
  private final Map<Long, Product> productsMap;

  public void createProduct(Product product) {
    productsMap.put(product.getId(), product);
  }

  public Optional<Product> getProductById(long productId) {
    return Optional.ofNullable(productsMap.get(productId));
  }

  public List<Product> getProducts() {
    return (List<Product>) productsMap.values();
  }
}
