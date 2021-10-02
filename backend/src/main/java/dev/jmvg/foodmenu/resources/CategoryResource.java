package dev.jmvg.foodmenu.resources;

import dev.jmvg.dto.CategoryDTO;
import dev.jmvg.foodmenu.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO categoryDTO = categoryService.findById(id);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.save(categoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
