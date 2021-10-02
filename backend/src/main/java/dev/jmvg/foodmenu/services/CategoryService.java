package dev.jmvg.foodmenu.services;

import dev.jmvg.dto.CategoryDTO;
import dev.jmvg.exceptions.EntityNotFoundException;
import dev.jmvg.foodmenu.entities.Category;
import dev.jmvg.foodmenu.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryDTO::new)
                            .collect(Collectors.toList());


    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Category obj = categoryRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(obj);
    }

}
