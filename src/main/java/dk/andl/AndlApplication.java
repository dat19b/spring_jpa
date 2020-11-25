package dk.andl;

import dk.andl.model.Category;
import dk.andl.model.Recipe;
import dk.andl.repository.CategoryRepository;
import dk.andl.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AndlApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndlApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDummyData(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        return args -> {
            Recipe recipeOne = new Recipe("new recipe 1");

            recipeRepository.save(recipeOne);

            Category categoryOne = new Category("new category 1");
            Category categoryTwo = new Category("new category 2");
            Category categoryThree = new Category("new category 3");

            categoryRepository.saveAll(Arrays.asList(categoryOne, categoryTwo, categoryThree));

            Set<Category> categoriesToAdd = new HashSet<>();
            categoriesToAdd.addAll(Arrays.asList(categoryOne, categoryTwo, categoryThree));
            recipeOne.setCategories(categoriesToAdd);

            // update the recipe
            recipeRepository.save(recipeOne);
        };
    }
}
