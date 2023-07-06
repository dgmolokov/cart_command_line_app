package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
  private final ProductService productService;

  @GetMapping("/all")
  public ResponseEntity<List<Product>> getAllProducts() {
    return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Product> getProduct(@RequestParam long id) {
    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
  }

  @DeleteMapping
  public void deleteProduct(@RequestParam long id) {
    productService.removeProduct(id);
  }
}
