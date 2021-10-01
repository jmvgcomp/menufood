package dev.jmvg.foodmenu.resources;

import dev.jmvg.foodmenu.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    public ResponseEntity<List<Category>> findAll(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1L, "Lanches"));
        categoryList.add(new Category(2L, "Brasileira"));
        return ResponseEntity.ok().body(categoryList);
    }


}
