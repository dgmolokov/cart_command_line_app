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
    var productOptional = productRepository.getProductById(productId);
    if (productOptional.isPresent()) {
      var product = productOptional.get();
      productsInCartWithQuantity.computeIfPresent(product, (key, value) -> value + DEFAULT_INCREMENT);
      productsInCartWithQuantity.putIfAbsent(product, DEFAULT_INCREMENT);
      return product;
    } else {
      System.out.println("No such product in store!");
      return null;
    }
  }

  public Product removeProduct(long productId) {
    var productOptional = productRepository.getProductById(productId);
    if (productOptional.isPresent()) {
      var product = productOptional.get();
      var quantity = productsInCartWithQuantity.get(product);
      if (quantity == null) {
        System.out.println("This product is not in the cart!");
      } else {
        productsInCartWithQuantity.remove(product);
      }
      return product;
    } else {
      System.out.println("No such product in store!");
      return null;
    }
  }
}
