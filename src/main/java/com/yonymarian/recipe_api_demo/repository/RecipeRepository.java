package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.utils.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    public List<Recipe> findByName(String name);
    public List<Recipe> findByNameAndAuthor(String name, User author);
    public List<Recipe> findByAuthor(User author);
    public List<Recipe> findByDifficulty(Difficulty difficulty);

    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE KEY(i) IN :ingredientNames")
    public List<Recipe> findByIngredients_IngredientNameIn(List<String> ingredientNames);

    @Query("SELECT r FROM Recipe r JOIN r.tags t where t.name IN :tagNames")
    public List<Recipe> findByTags_TagNameIn(List<String> tagNames);

}
