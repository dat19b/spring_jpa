package dk.clbo.controller;

import dk.clbo.model.Recipe;
import dk.clbo.model.Notes;
import dk.clbo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class RecipeController {

    RecipeRepository recipeRepository;
    // constructor injection
    public RecipeController(RecipeRepository recipeRepository ){
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public String index(){

        // find opskrift med id=1
        Optional<Recipe> x = recipeRepository.findById((long) 1);
        if (x.isPresent()) {
            System.out.println(x.get().getDescription());
        }

        // hent alle opskrifter
        for (Recipe r : recipeRepository.findAll()) {
            System.out.println(r.getDirections());
        }

        // brug af egen defineret findAllByCookTime
        for (Recipe s : recipeRepository.findAllByCookTime(20)) {
            System.out.println(s.getUrl());
        }

        // hente Notes.description fra Recipe
        for (Recipe z : recipeRepository.findAllByXxx("XXX")) {
            Notes n = z.getNotes();
            System.out.println(n.getDescription());
        }

        //whitepage - ingen index - research påkrævet
        return "index";
    }

}
