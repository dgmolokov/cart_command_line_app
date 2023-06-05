package com.example.cart_command_line_app;

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
}
