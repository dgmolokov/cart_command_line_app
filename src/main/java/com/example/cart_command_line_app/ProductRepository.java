package com.example.cart_command_line_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class ProductRepository {
  private final List<Product> products;
  private final Map<Long, Product> productsMap;

  public void createProduct(Product product) {
    products.add(product);
    productsMap.put(product.getId(), product);
  }

  public Product getProductByID(long productID) {
    return productsMap.get(productID);
  }
}
