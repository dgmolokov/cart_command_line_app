package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import org.jooq.lambda.Unchecked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductsController.class)
@AutoConfigureMockMvc
public class ProductsControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockBean
  ProductService productService;

  @Test
  public void getAllProducts() {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    var product1 = new Product(1, "product1", new BigDecimal(20000));
    doReturn(List.of(product0, product1)).when(productService).findAll();

     Unchecked.supplier(() -> mockMvc.perform(get("/products/all"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[0].id", is(0)))
      .andExpect(jsonPath("$[0].name", is("product0")))
      .andExpect(jsonPath("$[0].price", is(10000)))
      .andExpect(jsonPath("$[1].id", is(1)))
      .andExpect(jsonPath("$[1].name", is("product1")))
      .andExpect(jsonPath("$[1].price", is(20000)))).get();
  }

  @Test
  public void getProductById() {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    doReturn(product0).when(productService).getProductById(0);

    Unchecked.supplier(() -> mockMvc.perform(get("/products?id=0"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(0)))
      .andExpect(jsonPath("$.name", is("product0")))
      .andExpect(jsonPath("$.price", is(10000)))).get();
  }

  @Test
  public void createProduct() {
    var product = new Product(0, "product0", new BigDecimal(10000));
    doReturn(product).when(productService).createProduct(product);

    Unchecked.supplier(() -> mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": 0,\"name\": \"product0\",\"price\": \"10000\"}"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(0)))
      .andExpect(jsonPath("$.name", is("product0")))
      .andExpect(jsonPath("$.price", is(10000)))).get();
  }

  @Test
  public void deleteProduct() {
    doNothing().when(productService).removeProduct(0L);
    var mockMvcPerform = Unchecked.supplier(() -> mockMvc.perform(delete("/products?id=0")).andReturn());
    Assertions.assertNotEquals(500, mockMvcPerform.get().getResponse().getStatus());
  }
}
