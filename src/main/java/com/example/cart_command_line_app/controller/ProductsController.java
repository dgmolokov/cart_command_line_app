package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
  private final ProductService productService;

  @GetMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Product>> getAllProducts() {
    return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
  }

  @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> getProduct(@RequestParam(name = "id") long id) {
    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<HttpStatus> deleteProduct(@RequestParam(name = "id") long id) {
    productService.removeProduct(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
