package com.yonymarian.recipe_api_demo.service;

import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.repository.UserRepository;
import com.yonymarian.recipe_api_demo.utils.ApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //Helper method
    private User safeSearchForUser(UUID userId) {
        if (userId == null) {
            throw new ApiException("ID cannot be null");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Could not find a user with the provided ID"));
    }

    //Create
    public User createUser(String name, Set<Recipe> recipes) {
        User user;
        try {
            user = User.builder()
                    .name(Objects.requireNonNullElse(name, "ANONYMOUS"))
                    .recipes(recipes)
                    .build();
        } catch (RuntimeException e) {
            throw new ApiException("Error creating a user: " + e);
        }
        return userRepository.save(user);
    }

    //Read
    public User getUserById(UUID userId) {
        return safeSearchForUser(userId);
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }
    
    public List<User> getUsersByRecipeMade(Recipe recipe) {
        return userRepository.findByRecipes_Name(recipe.getName());
    }

    //Update
    public User changeUserName(UUID userId, String newName) {
        User userToUpdate = safeSearchForUser(userId);
        if (newName != null && !newName.isEmpty()) {
            userToUpdate.setName(newName);
        }
        return userRepository.save(userToUpdate);
    }
    
    public User addUserRecipe(UUID userId, Recipe newRecipe) {
        User userToUpdate = safeSearchForUser(userId);
        if (newRecipe != null) {
            userToUpdate.getRecipes().add(newRecipe);
        }
        return userRepository.save(userToUpdate);
    }

    public User deleteUserRecipe(UUID userId, Recipe targetRecipe) {
        User userToUpdate = safeSearchForUser(userId);
        if (targetRecipe != null) {
            userToUpdate.getRecipes().remove(targetRecipe);
        }
        return userRepository.save(userToUpdate);
    }

    //Delete
    public User deleteUser(UUID userId) {
        User userToDelete = safeSearchForUser(userId);
        userRepository.delete(userToDelete);
        return userToDelete;
    }
}
