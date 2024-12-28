package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
