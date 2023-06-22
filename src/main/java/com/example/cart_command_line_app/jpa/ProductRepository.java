package com.example.cart_command_line_app.jpa;

import com.example.cart_command_line_app.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductRepository {
  private final List<Product> products;

  public Product createProduct(Product product) {
    products.add(product);
    return product;
  }

  public void removeProduct(long productId) {
    var product = getProductById(productId);
    products.remove(product);
  }

  public Product getProductById(long productId) {
    return products.stream()
      .filter(product -> productId == product.getId())
      .findFirst()
      .orElseThrow(() -> new ObjectNotFoundException(String.format("Product with ID %d was not found!", productId)));
  }

  public List<Product> findAll() {
    return products;
  }
}
