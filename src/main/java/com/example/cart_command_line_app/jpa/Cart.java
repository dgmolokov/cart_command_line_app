package com.example.cart_command_line_app.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@AllArgsConstructor
public class Cart {
  private final int DEFAULT_INCREMENT = 1;
  private Map<Product, Integer> productsInCartWithQuantity;
  @Autowired
  private ProductRepository productRepository;

  public Product addProduct(long productId) {
    try {
      var product = productRepository.getProductById(productId);
      productsInCartWithQuantity.computeIfPresent(product, (key, value) -> value + DEFAULT_INCREMENT);
      productsInCartWithQuantity.putIfAbsent(product, DEFAULT_INCREMENT);
      return product;
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public void removeProduct(long productId) {
    try {
      var product = productRepository.getProductById(productId);
      var quantity = productsInCartWithQuantity.get(product);
      if (quantity == null) {
        System.out.println("This product is not in the cart!");
      } else {
        productsInCartWithQuantity.remove(product);
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
