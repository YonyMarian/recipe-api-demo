package com.yonymarian.recipe_api_demo.service;

import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.Tag;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.repository.RecipeRepository;
import com.yonymarian.recipe_api_demo.repository.TagRepository;
import com.yonymarian.recipe_api_demo.repository.UserRepository;
import com.yonymarian.recipe_api_demo.utils.Difficulty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yonymarian.recipe_api_demo.utils.ApiException;

import java.util.*;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    //Helper methods
    private Recipe safeSearchForRecipe(UUID recipeId) {
        if (recipeId == null) {
            throw new ApiException("ID cannot be null");
        }
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ApiException("Could not find a recipe with the provided ID"));
    }

    private Set<Tag> createTagSetFromNames(Set<String> tagNames) {
        Set<Tag> result = new HashSet<>();
        tagNames.forEach(tagName -> {
            result.add(new Tag(tagName.toUpperCase()));
        });
        return result;
    }

    //Create
    public Recipe createRecipe(String name, User author, Difficulty difficulty, Integer totalTime, Map<String, String> ingredients, List<String> steps, Set<Tag> tags) {
        Recipe recipe;
        //Set<Tag> tagSet = tagNames.isEmpty() ? new HashSet<Tag>() : createTagSetFromNames(tagNames);
        try {
            recipe = recipeRepository.save(Recipe.builder()
                    .name(name)
                    .author(author)
                    .difficulty(Objects.requireNonNullElse(difficulty, Difficulty.NONE_PROVIDED))
                    .totalTime(totalTime)
                    .ingredients(ingredients)
                    .steps(steps)
                    .tags(Objects.requireNonNullElse(tags, new HashSet<Tag>()))
                    .build()
            );
        } catch (Exception e) {
            throw new ApiException("Some parameter was improperly passed: " + e.getMessage());
        }
        return recipeRepository.save(recipe);
    }

    //Read
    public Recipe getRecipeById(UUID id) {
        return safeSearchForRecipe(id);
    }

    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findByName(name);
    }

    public List<Recipe> getRecipesByNameAndAuthor(String name, User author) {
        return recipeRepository.findByNameAndAuthor(name, author);
    }

    public List<Recipe> getRecipesByAuthor(User author) {
        return recipeRepository.findByAuthor(author);
    }

    public List<Recipe> getRecipesByDifficulty(Difficulty difficulty) {
        return recipeRepository.findByDifficulty(difficulty);
    }

    public List<Recipe> getRecipesByIngredients(List<String> ingredientNames) {
        return recipeRepository.findByIngredients_IngredientNameIn(ingredientNames);
    }

    public List<Recipe> getRecipesByTags(List<String> tags) {
        return recipeRepository.findByTags_TagNameIn(tags);
    }

    //Update

    //updates single-field columns
    //
    public Recipe updateRecipeMetadata(UUID recipeId, String newName, Difficulty newDifficulty, Integer newTotalTime) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        if (newName != null && !newName.isEmpty()) {
            recipeToUpdate.setName(newName);
        }
        if (newDifficulty != null) {
            recipeToUpdate.setDifficulty(newDifficulty);
        }
        if (newTotalTime != null && newTotalTime > 0) {
            recipeToUpdate.setTotalTime(newTotalTime);
        }
        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe addOrEditIngredients(UUID recipeId, List<String> ingredientNames, List<String> ingredientAmounts) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        if (ingredientAmounts != null && ingredientNames != null) {
            for (int i = 0; i < ingredientNames.size(); i++) {
                String ingredient = ingredientNames.get(i);
                String amount = ingredientAmounts.get(i);
                recipeToUpdate.getIngredients().put(ingredient, amount);
            }
        }
        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe deleteIngredient(UUID recipeId, String ingredientName) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        if (ingredientName != null && !ingredientName.isEmpty()) {
            recipeToUpdate.getIngredients().remove(ingredientName);
        }
        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe addStep(UUID recipeId, String newStep, int index) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        if (index <= 0) { index = 0; }
        if (index >= recipeToUpdate.getSteps().size()) { index = recipeToUpdate.getSteps().size() - 1; }
        if (newStep != null) {
            recipeToUpdate.getSteps().add(index, newStep);
        }
        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe deleteStep(UUID recipeId, int index) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        if (index <= 0 || index >= recipeToUpdate.getSteps().size()) {
            throw new ApiException("No step exists at this index");
        }
        recipeToUpdate.getSteps().remove(index);
        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe addTags(UUID recipeId, List<String> newTagNames) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        if (newTagNames != null) {
            newTagNames.forEach((newTagName) -> {
                //attempt to search for a tag with the given name
                List<Tag> newTagWrapper = tagRepository.findByName(newTagName);
                if (newTagWrapper.isEmpty()) { //if a name is not found, then:
                    newTagWrapper.add( //create a new tag within the wrapper
                            tagRepository.save( //and save it to the Tag Repository
                                    new Tag(newTagName.toUpperCase()
                                    )
                            )
                    );
                }
                recipeToUpdate.getTags().add(newTagWrapper.getFirst());
            });
        }
        return recipeRepository.save(recipeToUpdate);
    }

    public Recipe removeTag(UUID recipeId, UUID tagId) {
        Recipe recipeToUpdate = safeSearchForRecipe(recipeId);
        Tag tagToRemove = tagRepository.findById(tagId)
                .orElseThrow(() -> new ApiException("Could not find a tag with the provided ID"));
        recipeToUpdate.getTags().remove(tagToRemove);
        return recipeRepository.save(recipeToUpdate);
    }

    //Delete
    public Recipe deleteRecipe(UUID recipeId) {
        Recipe recipeToDelete = safeSearchForRecipe(recipeId);
        recipeRepository.delete(recipeToDelete);
        return recipeToDelete;
    }

}
