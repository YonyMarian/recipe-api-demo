package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.Tag;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.service.RecipeService;
import com.yonymarian.recipe_api_demo.utils.Difficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

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

    @PutMapping("/{id}/ingredients/add-edit")
    public ResponseEntity<Recipe> addOrEditRecipeIngredients(@PathVariable UUID id, @RequestParam List<String> ingredientNames, @RequestParam List<String> ingredientAmounts) {
        if (ingredientNames.size() != ingredientAmounts.size()) {
            return ResponseEntity.badRequest()
                    .header("Reason", "number of ingredient names must be same as number of ingredient amounts")
                    .build();
        }
        return ResponseEntity.ok(recipeService.addOrEditIngredients(id, ingredientNames, ingredientAmounts));
    }

    @PutMapping("/{id}/ingredients/delete")
    public ResponseEntity<Recipe> deleteRecipeIngredients(@PathVariable UUID id, @RequestParam String ingredientName) {
        Recipe targetRecipe = recipeService.deleteIngredient(id, ingredientName);
        return ResponseEntity.ok(targetRecipe);
    }

    @PutMapping("/{id}/steps/add")
    public ResponseEntity<Recipe> addRecipeStep(@PathVariable UUID id, @RequestParam String step, @RequestParam int index) {
        Recipe targetRecipe = recipeService.addStep(id, step, index);
        return ResponseEntity.ok(targetRecipe);
    }

    @PutMapping("/{id}/steps/delete")
    public ResponseEntity<Recipe> deleteRecipeStep(@PathVariable UUID id, @RequestParam int index) {
        Recipe targetRecipe = recipeService.deleteStep(id, index);
        return ResponseEntity.ok(targetRecipe);
    }

    @PutMapping("/{id}/tags/add")
    public ResponseEntity<Recipe> addRecipeTags(@PathVariable UUID id, @RequestParam List<String> tags) {
        Recipe targetRecipe = recipeService.addTags(id, tags);
        return ResponseEntity.ok(targetRecipe);
    }

    @PutMapping("/{id}/tags/remove")
    public ResponseEntity<Recipe> removeRecipeTag(@PathVariable UUID id, @RequestParam UUID tagId) {
        Recipe targetRecipe = recipeService.removeTag(id, tagId);
        return ResponseEntity.ok(targetRecipe);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable UUID id) {
        Recipe recipeToDelete = recipeService.deleteRecipe(id);
        return ResponseEntity.ok(recipeToDelete);
    }



}
