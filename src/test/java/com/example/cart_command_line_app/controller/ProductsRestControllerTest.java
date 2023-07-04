package com.example.cart_command_line_app.controller;

import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.service.ProductService;
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
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
public class ProductsRestControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockBean
  ProductService productService;

  @Test
  @DisplayName("GET /rest/products/json/get_all")
  public void getAllProductsJsonTest() throws Exception {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    var product1 = new Product(1, "product1", new BigDecimal(20000));
    doReturn(List.of(product0, product1)).when(productService).findAll();

    mockMvc.perform(get("/rest/products/json/get_all"))
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
  @DisplayName("GET /rest/products/get/json?id=0")
  public void getProductByIdJsonTest() throws Exception {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    doReturn(product0).when(productService).getProductById(0);

    mockMvc.perform(get("/rest/products/json/get?id=0"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(0)))
      .andExpect(jsonPath("$.name", is("product0")))
      .andExpect(jsonPath("$.price", is(10000)));
  }

  @Test
  @DisplayName("GET /rest/products/get/xml?id=0")
  public void getProductByIdXmlTest() throws Exception {
    var product0 = new Product(0, "product0", new BigDecimal(10000));
    doReturn(product0).when(productService).getProductById(0);

    mockMvc.perform(get("/rest/products/xml/get?id=0"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_XML))
      .andExpect(xpath("/Product/id").string("0"))
      .andExpect(xpath("/Product/name").string("product0"))
      .andExpect(xpath("/Product/price").string("10000"));
  }
}
