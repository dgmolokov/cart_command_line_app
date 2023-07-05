package com.example.cart_command_line_app.service;

import com.example.cart_command_line_app.Application;
import com.example.cart_command_line_app.exception.ObjectNotFoundException;
import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.jpa.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

@SpringBootTest(classes = Application.class)
public class CartServiceTest {
  @Autowired
  CartService cartService;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ProductService productService;

  @BeforeEach
  public void init() {
    productRepository = new ProductRepository(new ArrayList<>());
    productService = new DefaultProductService(productRepository);
    IntStream.range(0, 2).forEach(
      i -> productService.createProduct(new Product(i, String.format("Product%d", i), new BigDecimal(10000)))
    );
    cartService = new CartService(new HashMap<>(), productService);
  }

  @Test
  public void addAllProductsTest() {
    long productId = 0;
    Assertions.assertEquals(productService.getProductById(productId), cartService.addAllProducts(productId, 5));
  }

  @Test
  public void updateQuantityTest() {
    long productId = 0;
    cartService.addAllProducts(productId, 1);
    Assertions.assertEquals(productService.getProductById(productId), cartService.updateQuantity(productId, 5));
  }

  @Test
  public void addAllProductAndFindAllTest() {
    long productId = 0;
    int quantity = 5;
    cartService.addAllProducts(productId, quantity);
    var expectedResult = new HashMap<Product, Integer>();
    expectedResult.put(productService.getProductById(productId), quantity);
    Assertions.assertEquals(expectedResult, cartService.findAll());
  }

  @Test
  public void updateQuantityAndFindAllTest() {
    long productId = 0;
    int newQuantity = 5;
    cartService.addAllProducts(productId, 1);
    var expectedResult = new HashMap<Product, Integer>();
    expectedResult.put(productService.getProductById(productId), newQuantity);
    cartService.updateQuantity(productId, newQuantity);
    Assertions.assertEquals(expectedResult, cartService.findAll());
  }

  @Test
  public void removeProductAndFindAllTest() {
    long productId = 0;
    cartService.addAllProducts(productId, 1);
    cartService.removeProduct(productId);
    Assertions.assertEquals(new HashMap<Product, Integer>(), cartService.findAll());
  }

  @Test
  public void removeMissingProductTest() {
    Assertions.assertThrows(ObjectNotFoundException.class, () -> cartService.removeProduct(0));
  }
}
