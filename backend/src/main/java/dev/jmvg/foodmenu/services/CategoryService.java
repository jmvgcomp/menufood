package dev.jmvg.foodmenu.services;

import dev.jmvg.dto.CategoryDTO;
import dev.jmvg.exceptions.DatabaseIntegrityException;
import dev.jmvg.exceptions.ResourceNotFoundException;
import dev.jmvg.foodmenu.entities.Category;
import dev.jmvg.foodmenu.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        Page<Category> list = categoryRepository.findAll(pageable);
        return list.map(CategoryDTO::new);
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
            BeanUtils.copyProperties(categoryDTO, category, "id");
            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }

    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found "+ id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseIntegrityException("Integrity violation");
        }

    }
}
