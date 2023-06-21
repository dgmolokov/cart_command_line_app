package com.example.cart_command_line_app.jpa;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {
  private long id;
  private String name;
  private BigDecimal price;
}
