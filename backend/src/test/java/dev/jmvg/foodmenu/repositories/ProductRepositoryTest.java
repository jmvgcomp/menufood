package dev.jmvg.foodmenu.repositories;

import dev.jmvg.foodmenu.entities.Product;
import dev.jmvg.foodmenu.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long noExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        noExistingId = 0L;
        countTotalProducts = 30L;
    }


    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = ProductFactory.createProduct();
        product.setId(null);
        product = productRepository.save(product);
        Assertions.assertNotNull(product.getId());

        Assertions.assertEquals(countTotalProducts+1, product.getId());
    }


    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
          productRepository.deleteById(noExistingId);
        });
    }

}
