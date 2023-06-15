package com.example.cart_command_line_app.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Product {
  private long id;
  private String name;
  private int price;

  @Override
  public String toString() {
    return String.format("ID: %d, Name: %s, Price: %d\n", getId(), getName(), getPrice());
  }
}
