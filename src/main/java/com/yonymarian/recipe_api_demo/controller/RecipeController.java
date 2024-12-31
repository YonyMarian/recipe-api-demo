package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.service.RecipeService;
import com.yonymarian.recipe_api_demo.utils.Difficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable UUID id) {
        Recipe targetRecipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(targetRecipe);
    }

    @GetMapping("/search/by-metadata")
    public ResponseEntity<List<Recipe>> getRecipesByMetadata(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) User author,
            @RequestParam(required = false) Difficulty difficulty
            ) {
        //check that at least one parameter is present
        if (name == null && author == null && difficulty == null) {
            return ResponseEntity.badRequest().header("Reason", "All search queries were null").build();
        }
        if (difficulty != null) {
            return ResponseEntity.ok(recipeService.getRecipesByDifficulty(difficulty));
        }
        if (name != null && author != null) {
            return ResponseEntity.ok(recipeService.getRecipesByNameAndAuthor(name, author));
        }
        if (name != null) {
            return ResponseEntity.ok(recipeService.getRecipesByName(name));
        }
        if (author != null) {
            return ResponseEntity.ok(recipeService.getRecipesByAuthor(author));
        }
        return ResponseEntity.badRequest().header("Reason", "This combination of parameters is invalid").build();
    }

    @GetMapping("/search/by-ingredients")
    public ResponseEntity<List<Recipe>> getRecipesByIngredients(@RequestParam List<String> ingredients) {
        List<Recipe> targetRecipes = recipeService.getRecipesByIngredients(ingredients);
        return ResponseEntity.ok(targetRecipes);
    }

    @GetMapping("/search/by-tags")
    public ResponseEntity<List<Recipe>> getRecipesByTags(@RequestParam List<String> tags) {
        List<Recipe> targetRecipes = recipeService.getRecipesByTags(tags);
        return ResponseEntity.ok(targetRecipes);
    }

    @PostMapping("/create")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipePayload) {
        return new ResponseEntity<>(
                recipeService.createRecipe(
                    recipePayload.getName(),
                    recipePayload.getAuthor(),
                    recipePayload.getDifficulty(),
                    recipePayload.getTotalTime(),
                    recipePayload.getIngredients(),
                    recipePayload.getSteps(),
                    recipePayload.getTags()
            ), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}/update-metadata")
    public ResponseEntity<Recipe> updateRecipeMetadata(
            @RequestParam UUID id,
            @RequestParam String name,
            @RequestParam Difficulty difficulty,
            @RequestParam(name = "total-time") Integer totalTime
    ) {
        //check that at least one parameter is provided
        if (name == null && difficulty == null && totalTime == null) {
            return ResponseEntity.badRequest().header("Reason", "No parameters were provided for updating").build();
        }
        return ResponseEntity.ok(recipeService.updateRecipeMetadata(id, name, difficulty, totalTime));
    }


}
