package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    public List<Recipe> findByName(String name);
    public List<Recipe> findByNameAndAuthor(String name, String author);
    public List<Recipe> findByAuthor(String author);
    public List<Recipe> findByDifficulty(String difficulty);
    public List<Recipe> findByIngredients_IngredientNameIn(List<String> ingredientNames);
    public List<Recipe> findByTags_TagNameIn(List<String> tags);

}
