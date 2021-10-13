package dev.jmvg.foodmenu.services;

import dev.jmvg.dto.CategoryDTO;
import dev.jmvg.dto.ProductDTO;
import dev.jmvg.exceptions.DatabaseIntegrityException;
import dev.jmvg.exceptions.ResourceNotFoundException;
import dev.jmvg.foodmenu.entities.Category;
import dev.jmvg.foodmenu.entities.Product;
import dev.jmvg.foodmenu.repositories.CategoryRepository;
import dev.jmvg.foodmenu.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product obj = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(obj, obj.getCategories());
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO){
        Product product = new Product();
        copyProductDtoWithCategories(productDTO, product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
        try{
            Product product = productRepository.getOne(id);
            copyProductDtoWithCategories(productDTO, product, "id");
            return new ProductDTO(product);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found "+ id);
        }
    }

    private void copyProductDtoWithCategories(ProductDTO productDTO, Product product, String... ignoreProperties) {
        BeanUtils.copyProperties(productDTO, product, ignoreProperties);
        product.getCategories().clear();
        for (CategoryDTO categoryDTO : productDTO.getCategories()) {
            Category category = categoryRepository.getOne(categoryDTO.getId());
            product.getCategories().add(category);
        }
    }

    public void delete(Long id){
        try{
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseIntegrityException("Integrity violation");
        }
    }

}
