package com.example.cart_command_line_app.service;

import com.example.cart_command_line_app.exception.ObjectNotFoundException;
import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@AllArgsConstructor
public class Cart {
  private Map<Product, Integer> productsInCartWithQuantity;
  @Autowired
  private ProductService productService;

  public Product addAllProducts(long productId, int quantity) {
    var product = productService.getProductById(productId);
    productsInCartWithQuantity.put(product, quantity);
    return product;
  }

  public Map<Product, Integer> findAll() {
    return productsInCartWithQuantity;
  }

  public Product updateQuantity(long productId, int newQuantity) {
    var product = productService.getProductById(productId);
    productsInCartWithQuantity.replace(product, newQuantity);
    return product;
  }

  public void removeProduct(long productId) {
    var product = productService.getProductById(productId);
    var quantity = productsInCartWithQuantity.get(product);
    if (quantity == null) {
      throw new ObjectNotFoundException(String.format("The product with ID %d is not in the cart!", productId));
    } else {
      productsInCartWithQuantity.remove(product);
    }
  }
}
