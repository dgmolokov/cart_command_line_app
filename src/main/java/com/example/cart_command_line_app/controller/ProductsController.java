package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {
  ProductService productService;

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String getAllProducts(Model model) {
    var products = productService.findAll();
    model.addAttribute("products", products);
    return "all_products";
  }

  @GetMapping("/create")
  public String getForm(Model model) {
    Product product = new Product();
    model.addAttribute("product", product);
    return "add_product";
  }

  @PostMapping("/create")
  public String createProduct(Product product) {
    productService.createProduct(product);
    return "add_product";
  }
}
