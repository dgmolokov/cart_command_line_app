package com.example.cart_command_line_app.service;

import com.example.cart_command_line_app.Application;
import com.example.cart_command_line_app.exception.ObjectNotFoundException;
import com.example.cart_command_line_app.service.Cart;
import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.jpa.ProductRepository;
import com.example.cart_command_line_app.service.DefaultProductService;
import com.example.cart_command_line_app.service.ProductService;
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
public class CartTest {
  @Autowired
  Cart cart;

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
    cart = new Cart(new HashMap<>(), productService);
  }

  @Test
  public void addAllProductsTest() {
    long productId = 0;
    Assertions.assertEquals(productService.getProductById(productId), cart.addAllProducts(productId, 5));
  }

  @Test
  public void updateQuantityTest() {
    long productId = 0;
    cart.addAllProducts(productId, 1);
    Assertions.assertEquals(productService.getProductById(productId), cart.updateQuantity(productId, 5));
  }

  @Test
  public void addAllProductAndFindAllTest() {
    long productId = 0;
    int quantity = 5;
    cart.addAllProducts(productId, quantity);
    var expectedResult = new HashMap<Product, Integer>();
    expectedResult.put(productService.getProductById(productId), quantity);
    Assertions.assertEquals(expectedResult, cart.findAll());
  }

  @Test
  public void updateQuantityAndFindAllTest() {
    long productId = 0;
    int newQuantity = 5;
    cart.addAllProducts(productId, 1);
    var expectedResult = new HashMap<Product, Integer>();
    expectedResult.put(productService.getProductById(productId), newQuantity);
    cart.updateQuantity(productId, newQuantity);
    Assertions.assertEquals(expectedResult, cart.findAll());
  }

  @Test
  public void removeProductAndFindAllTest() {
    long productId = 0;
    cart.addAllProducts(productId, 1);
    cart.removeProduct(productId);
    Assertions.assertEquals(new HashMap<Product, Integer>(), cart.findAll());
  }

  @Test
  public void removeMissingProductTest() {
    Assertions.assertThrows(ObjectNotFoundException.class, () -> cart.removeProduct(0));
  }
}
