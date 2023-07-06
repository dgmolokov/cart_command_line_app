package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
  private final ProductService productService;

  @GetMapping("/all")
  public List<Product> getAllProducts() {
    return productService.findAll();
  }

  @GetMapping
  public Product getProduct(@RequestParam long id) {
    return productService.getProductById(id);
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  @DeleteMapping
  public void deleteProduct(@RequestParam long id) {
    productService.removeProduct(id);
  }
}
