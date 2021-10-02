package dev.jmvg.foodmenu.services;

import dev.jmvg.dto.CategoryDTO;
import dev.jmvg.foodmenu.entities.Category;
import dev.jmvg.foodmenu.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

}
