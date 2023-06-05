package com.example.cart_command_line_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
@Getter
@AllArgsConstructor
public class Cart {
  private final int DEFAULT_INCREMENT = 1;
  private Map<Product, Integer> productsInCartWithQuantity;
  @Autowired
  private ProductRepository productRepository;

  public void addProduct(long productId) {
    var product = productRepository.getProductByID(productId);
    productsInCartWithQuantity.computeIfPresent(product, (key, value) -> value + DEFAULT_INCREMENT);
    productsInCartWithQuantity.putIfAbsent(product, DEFAULT_INCREMENT);
  }

  public void removeProduct(long productId) {
    var product = productRepository.getProductByID(productId);
    var quantity = productsInCartWithQuantity.get(product);
    if (quantity == null) {
      System.out.println("This product is not in the cart!");
    } else if (quantity == DEFAULT_INCREMENT) {
      productsInCartWithQuantity.remove(product);
    } else {
      productsInCartWithQuantity.replace(product, quantity - DEFAULT_INCREMENT);
    }
  }
}
