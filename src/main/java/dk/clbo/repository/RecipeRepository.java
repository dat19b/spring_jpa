package dk.clbo.repository;

import dk.clbo.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    //Optional<Recipe> findByCookTime(int cooktime);
    Set<Recipe> findAllByCookTime(int cooktime);
    Set<Recipe> findAllByXxx(String x);
}
