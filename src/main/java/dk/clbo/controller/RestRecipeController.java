package dk.clbo.controller;

import dk.clbo.model.Category;
import dk.clbo.model.Ingredient;
import dk.clbo.model.Notes;
import dk.clbo.model.Recipe;
import dk.clbo.repository.CategoryRepository;
import dk.clbo.repository.IngredientRepository;
import dk.clbo.repository.NotesRepository;
import dk.clbo.repository.RecipeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class RestRecipeController {

    RecipeRepository recipeRepository;
    NotesRepository notesRepository;
    IngredientRepository ingredientRepository;
    CategoryRepository categoryRepository;

    // constructor injection
    public RestRecipeController(RecipeRepository recipeRepository,
                                NotesRepository notesRepository,
                                IngredientRepository ingredientRepository,
                                CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.notesRepository = notesRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
    }

    // HTTP Get
    @GetMapping("/recipe")
    public Iterable<Recipe> findAll(){

        // find all recipes
        return recipeRepository.findAll();
    }

    // HTTP Get by ID
    @GetMapping("/recipe/{id}")
    public ResponseEntity<Optional<Recipe>> findById(@PathVariable Long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()){
            return ResponseEntity.status(200).body(recipe); // OK
        } else {
            return ResponseEntity.status(404).body(recipe); // Not found
        }
    }

    // HTTP Post, ie. create
    @CrossOrigin(origins = "*", exposedHeaders = "Location")
    @PostMapping(value="/recipe", consumes={"application/json"})
    public ResponseEntity<String> create(@RequestBody Recipe r){
        Recipe _recipe = new Recipe(r.getDescription(), r.getPrepTime(), r.getCookTime(), r.getServings(), r.getSource(), r.getUrl(), r.getDirections(), r.getXxx());
        Notes _notes = new Notes(r.getNotes().getDescription(),_recipe);
        _recipe.setNotes(_notes);


        Set<Ingredient> _ingredients = r.getIngredients();
        for (Ingredient ingredient : _ingredients){
            ingredient.setRecipe(_recipe);
            //ingredientRepository.save(ingredient);
        }
        _recipe.setIngredients(_ingredients);

/*
        Set<Category> _categories = r.getCategories();
        for (Category category : _categories){
            category.getRecipes().add(_recipe);
        }
        _recipe.setCategories(_categories);
*/
        //notesRepository.save(_notes);
        Recipe recipe = recipeRepository.save(_recipe);

        return ResponseEntity.status(201).header("Location", "/recipe/" + r.getId()).body("{'Msg': 'post created'}");
    }

    // HTTP PUT, ie. update
    @PutMapping("/recipe")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Recipe r){
        //get recipeById
        //check og opdater category - fjern og tilføj
        //save recipe
        recipeRepository.save(r);
        return ResponseEntity.status(204).body("{'msg':'Updated'}");
    }

    // HTTPDelete
    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        //check at opskriften findes
        if(!recipe.isPresent()){
            return ResponseEntity.status(404).body("{'msg':'Not found'"); // Not found
        }

        Recipe r = recipe.get();
        //slet først referencerne til recipe i categories
        for (Category c: r.getCategories()){
            c.getRecipes().remove(r);
        }

        //derefter kan categories slettes fra recipe
        r.setCategories(null);

        //og opdateres (nu uden category mappings)
        recipeRepository.save(r);

        //til sidst kan recipe slettes uden at bryde referentiel integritet
        recipeRepository.deleteById(id);

        return ResponseEntity.status(200).body("{'msg':'Deleted'}");
    }
}
