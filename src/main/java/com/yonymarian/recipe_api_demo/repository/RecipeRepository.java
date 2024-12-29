package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    public List<Recipe> findByName();
    public List<Recipe> findByNameAndAuthor();
    public List<Recipe> findByAuthor();
    public List<Recipe> findByDifficulty();
    public List<Recipe> findByIngredients_IngredientName();
    public List<Recipe> findByTags_TagName();

}
