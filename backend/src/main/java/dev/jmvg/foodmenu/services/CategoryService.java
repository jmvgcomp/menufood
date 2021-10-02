package dev.jmvg.foodmenu.services;

import dev.jmvg.dto.CategoryDTO;
import dev.jmvg.exceptions.ResourceNotFoundException;
import dev.jmvg.foodmenu.entities.Category;
import dev.jmvg.foodmenu.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Category obj = categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(obj);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try{
            Category category = categoryRepository.getOne(id);
            category.setName(categoryDTO.getName());
            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }

    }
}
