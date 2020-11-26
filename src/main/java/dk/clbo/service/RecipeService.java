package dk.clbo.service;

import dk.clbo.model.Recipe;
import dk.clbo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {

    //Annoter med autowired til repository
    @Autowired
    RecipeRepository recipeRepository;

    public Recipe findById(Long id) {
        //findById giver en optional
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        //hvis der ikke findes et product på id, kastes der en fejl
        if (!recipeOptional.isPresent())
        {
            throw new RuntimeException("Recipe not found");
        }
        //returner product vha. .get()
        return recipeOptional.get();
    }

    public Set<Recipe> findAll()
    {
        //kald repo.findAll og returner
        Set<Recipe> recipes = new HashSet<>();
        for(Recipe recipe:recipeRepository.findAll()) {
            recipes.add(recipe);
        }
        return recipes;
    }

    // ved create genererer Hibernate id
    public void create(Recipe recipe)
    {
        recipeRepository.save(recipe);
    }

    // ved update skal id være sat
    public void update(Recipe recipe) { recipeRepository.save(recipe); }

    public void delete(Long id) {
        //bør tilføjes rutine til sletning af referencer fra categories til recipe
        recipeRepository.deleteById(id);
    }

}
