package dev.jmvg.foodmenu.factory;

import dev.jmvg.dto.ProductDTO;
import dev.jmvg.foodmenu.entities.Category;
import dev.jmvg.foodmenu.entities.Product;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct(){
        Product product = new Product(1L, "X-Burguer", "Cebola, tomate, alface e carne", 20.0, "http://img.com/x-burguer", Instant.now());
        product.getCategories().add(new Category(2L, "Lanches"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
