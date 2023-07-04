package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/products")
public class ProductsRestController {
  @Autowired
  ProductService productService;

  @GetMapping(value = "/json/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Product> getAllProductsAsJson() {
    return productService.findAll();
  }

  @GetMapping(value = "/json/get", produces = MediaType.APPLICATION_JSON_VALUE)
  public Product getProductAsJson(@RequestParam(name = "id") long id) {
    return productService.getProductById(id);
  }

  @GetMapping(value = "/xml/get", produces = MediaType.APPLICATION_XML_VALUE)
  public Product getProductAsXML(@RequestParam(name = "id") long id) {
    return productService.getProductById(id);
  }
}
