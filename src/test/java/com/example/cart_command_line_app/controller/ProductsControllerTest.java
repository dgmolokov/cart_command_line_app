package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductsController.class)
@AutoConfigureMockMvc
public class ProductsControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockBean
  ProductService productService;

  @Test
  @DisplayName("POST /products/create")
  public void createProductTest() throws Exception {
    mockMvc.perform(post("/products/create")
        .param("id", "0")
        .param("name", "product0")
        .param("price", "20000"))
      .andExpect(status().isOk());
    verify(productService).createProduct(new Product(0, "product0", new BigDecimal(20000)));
  }

  @Test
  @DisplayName("GET /products")
  public void getAllProductsTest() throws Exception {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    var product1 = new Product(1, "product1", new BigDecimal(20000));
    doReturn(List.of(product0, product1)).when(productService).findAll();

    mockMvc.perform(get("/products"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("text/html;charset=UTF-8"))
      .andExpect(content().string(containsString("<h1>All products:</h1>")))
      .andExpect(content().string(containsString("<p>ID: 0, Name: product0, Price: 10000</p>")))
      .andExpect(content().string(containsString("<p>ID: 1, Name: product1, Price: 20000</p>")));
  }

  @Test
  @DisplayName("GET /products/create")
  public void getCreateFormTest() throws Exception {
    mockMvc.perform(get("/products/create"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("text/html;charset=UTF-8"))
      .andExpect(content().string(containsString("<form action=\"/products/create\" method=\"post\">")))
      .andExpect(content().string(containsString("ID: <input type=\"text\" id=\"id\" name=\"id\" value=\"0\"/>")))
      .andExpect(content().string(containsString("Name: <input type=\"text\" id=\"name\" name=\"name\" value=\"\"/>")))
      .andExpect(content().string(containsString("Price: <input type=\"text\" id=\"price\" name=\"price\" value=\"\"/>")))
      .andExpect(content().string(containsString("<button type=\"submit\">Save</button>")));
  }
}
