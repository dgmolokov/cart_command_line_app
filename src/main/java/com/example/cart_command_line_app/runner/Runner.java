package com.example.cart_command_line_app.runner;

import com.example.cart_command_line_app.jpa.Cart;
import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class Runner implements CommandLineRunner {
  private final ProductRepository productRepository;
  private final Cart cart;
  private final Scanner scanner = new Scanner(System.in);

  @Override
  public void run(String... args) {
    createSampleProducts();
    addFiveRandomProductsToCart(cart);
    System.out.println("Welcome!");
    showCart(cart);
    startInteraction();
    scanner.close();
  }

  private void createSampleProducts() {
    for (int i = 0; i < 10; i++) {
      productRepository.createProduct(new Product(i, "Product" + i, 20000));
    }
  }

  private void addFiveRandomProductsToCart(Cart cart) {
    for (int i = 0; i < 5; i++) {
      cart.addProduct(new Random().nextLong(productRepository.getProductsMap().size()));
    }
  }

  private void startInteraction() {
    while (true) {
      System.out.println("""
        ----------------
        Select operation:
        1. Add product to cart
        2. Remove product from cart
        3. Show your cart
        4. Show all products
        5. Exit
        Type number of operation to continue:""");
      int input = scanner.nextInt();
      switch (input) {
        case 1 -> addProduct();
        case 2 -> removeProduct();
        case 3 -> showCart(cart);
        case 4 -> showAllProducts();
      }
      if (input == 5) {
        break;
      }
    }
    System.out.println("Goodbye!");
  }

  private void showCart(Cart cart) {
    System.out.println("Your cart:");
    for (Map.Entry<Product, Integer> productAndQuantity : cart.getProductsInCartWithQuantity().entrySet()) {
      var product = productAndQuantity.getKey();
      var quantity = productAndQuantity.getValue();
      System.out.printf("%d items - %s", quantity, product);
    }
  }

  private void addProduct() {
    System.out.println("Type ID of product to add:");
    cart.addProduct(scanner.nextLong());
    System.out.println("Done!");
  }

  private void removeProduct() {
    System.out.println("Type ID of product to remove:");
    cart.removeProduct(scanner.nextLong());
    System.out.println("Done!");
  }

  private void showAllProducts() {
    for (Product product : productRepository.getProducts()) {
      System.out.print(product);
    }
  }
}
