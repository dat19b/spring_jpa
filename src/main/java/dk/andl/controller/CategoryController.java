package dk.andl.controller;

import dk.andl.model.Category;
import dk.andl.model.Recipe;
import dk.andl.repository.CategoryRepository;
import dk.andl.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/categories")
    public Category createCategory(/*@Valid*/ @RequestBody Category category) {
        return categoryRepository.save(category);
    }

}
