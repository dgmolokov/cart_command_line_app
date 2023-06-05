package com.example.cart_command_line_app.runner;

import com.example.cart_command_line_app.Cart;
import com.example.cart_command_line_app.Product;
import com.example.cart_command_line_app.ProductRepository;
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
    productRepository.createProduct(new Product(0, "Phone", 40000));
    productRepository.createProduct(new Product(1, "Notebook", 100000));
    productRepository.createProduct(new Product(2, "Keyboard", 3000));
    productRepository.createProduct(new Product(3, "Mouse", 2000));
    productRepository.createProduct(new Product(4, "Headset", 5000));
    productRepository.createProduct(new Product(5, "Monitor", 20000));
    productRepository.createProduct(new Product(6, "CPU", 40000));
    productRepository.createProduct(new Product(7, "RAM", 5000));
    productRepository.createProduct(new Product(8, "HDD", 7000));
    productRepository.createProduct(new Product(9, "SSD", 10000));
  }

  private void addFiveRandomProductsToCart(Cart cart) {
    for (int i = 0; i < 5; i++) {
      cart.addProduct(new Random().nextLong(productRepository.getProducts().size()));
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
      System.out.printf("ID: %d, Name: %s, Price: %d - %d items\n",
        product.getId(),
        product.getName(),
        product.getPrice(),
        quantity);
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
      System.out.printf("ID: %d, Name: %s, Price: %d\n", product.getId(), product.getName(), product.getPrice());
    }
  }
}
