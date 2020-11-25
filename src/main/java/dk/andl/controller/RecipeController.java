package dk.andl.controller;

import dk.andl.model.Recipe;
import dk.andl.repository.RecipeRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;


    @GetMapping("/recipes")
    public Iterable<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public Recipe getRecipeById(@PathVariable long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @PostMapping("/recipes")
    public Recipe createRecipe(/*@Valid*/ @RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @PatchMapping("/recipes/{id}")
    public Recipe createRecipe(@PathVariable long id, /*@Valid*/ @RequestBody Recipe recipe) {
        return recipeRepository.findById(id).map(foundRecipe -> {
            foundRecipe.setName(recipe.getName());
            foundRecipe.setCategories(recipe.getCategories());

            return recipeRepository.save(foundRecipe);
        }).orElseGet(() -> {
            // create a new one, this depends on your business logic
            return recipeRepository.save(recipe);
        });
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<String> createRecipe(@PathVariable long id) {
        try {
            recipeRepository.deleteById(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception exception) {
            return ResponseEntity.status(404).body("Not Found");
        }
    }
}
