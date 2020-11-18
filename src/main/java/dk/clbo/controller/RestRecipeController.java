package dk.clbo.controller;

import dk.clbo.model.Category;
import dk.clbo.model.Recipe;
import dk.clbo.repository.RecipeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RestRecipeController {

    RecipeRepository recipeRepository;
    // constructor injection
    public RestRecipeController(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
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
    @PostMapping("/recipe")
    public ResponseEntity<String> create(@RequestBody Recipe r){
        Recipe recipe = recipeRepository.save(r);
        return ResponseEntity.status(201).header("Location", "/post/" + recipe.getId()).body("{'Msg': 'post created'}");
    }

    // HTTP PUT, ie. update
    @PutMapping("/recipe")
    public ResponseEntity<String> update(@RequestBody Recipe r){
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
