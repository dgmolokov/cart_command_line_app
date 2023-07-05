package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductsController.class)
@AutoConfigureMockMvc
public class ProductsControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockBean
  ProductService productService;

  @Test
  @DisplayName("GET /products/get_all")
  public void getAllProductsTest() throws Exception {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    var product1 = new Product(1, "product1", new BigDecimal(20000));
    doReturn(List.of(product0, product1)).when(productService).findAll();

    mockMvc.perform(get("/products/get_all"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[0].id", is(0)))
      .andExpect(jsonPath("$[0].name", is("product0")))
      .andExpect(jsonPath("$[0].price", is(10000)))
      .andExpect(jsonPath("$[1].id", is(1)))
      .andExpect(jsonPath("$[1].name", is("product1")))
      .andExpect(jsonPath("$[1].price", is(20000)));
  }

  @Test
  @DisplayName("GET /products/get?id=0")
  public void getProductByIdTest() throws Exception {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    doReturn(product0).when(productService).getProductById(0);

    mockMvc.perform(get("/products/get?id=0"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(0)))
      .andExpect(jsonPath("$.name", is("product0")))
      .andExpect(jsonPath("$.price", is(10000)));
  }

  @Test
  @DisplayName("POST /products/create")
  public void createProductTest() throws Exception {
    var product = new Product(0, "product0", new BigDecimal(10000));
    doReturn(product).when(productService).createProduct(product);

    mockMvc.perform(post("/products/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(product)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(0)))
      .andExpect(jsonPath("$.name", is("product0")))
      .andExpect(jsonPath("$.price", is(10000)));
  }

  @Test
  @DisplayName("DELETE /products/delete")
  public void deleteProductTest() throws Exception {
    doNothing().when(productService).removeProduct(0L);

    mockMvc.perform(delete("/products/delete?id=0"))
      .andExpect(status().isNoContent());
  }
}
