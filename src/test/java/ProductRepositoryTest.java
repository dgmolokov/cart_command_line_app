import com.example.cart_command_line_app.Application;
import com.example.cart_command_line_app.exception.ObjectNotFoundException;
import com.example.cart_command_line_app.jpa.Product;
import com.example.cart_command_line_app.jpa.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = Application.class)
public class ProductRepositoryTest {
  @Autowired
  private ProductRepository productRepository;

  @BeforeEach
  public void init() {
    productRepository = new ProductRepository(new ArrayList<>());
  }

  @Test
  public void createProductTest() {
    var product = new Product(0, "product0", new BigDecimal(20000));
    Assertions.assertEquals(product, productRepository.createProduct(product));
  }

  @Test
  public void deleteProductTest() {
    var product = new Product(0, "product0", new BigDecimal(20000));
    productRepository.createProduct(product);
    productRepository.removeProduct(product.getId());
    Assertions.assertThrows(ObjectNotFoundException.class, () -> productRepository.getProductById(product.getId()));
  }

  @Test
  public void getProductTest() {
    var product = new Product(0, "product0", new BigDecimal(20000));
    productRepository.createProduct(product);
    Assertions.assertEquals(product, productRepository.getProductById(product.getId()));
  }

  @Test
  public void findAllTest() {
    var products = List.of(
      new Product(0, "product0", new BigDecimal(20000)),
      new Product(1, "product1", new BigDecimal(20000))
    );
    products.forEach(product -> productRepository.createProduct(product));
    Assertions.assertEquals(products, productRepository.findAll());
  }
}
