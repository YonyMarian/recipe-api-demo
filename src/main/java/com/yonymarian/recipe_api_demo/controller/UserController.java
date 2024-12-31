package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User targetUser = userService.getUserById(id);
        return ResponseEntity.ok(targetUser);
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<User>> getUsersByName(@RequestParam String name) {
        List<User> targetUsers = userService.getUsersByName(name);
        return ResponseEntity.ok(targetUsers);
    }

    @GetMapping("/search/by-recipe")
    public ResponseEntity<List<User>> getUsersByRecipeMade(@RequestBody Recipe recipe) {
        List<User> targetUsers = userService.getUsersByRecipeMade(recipe);
        return ResponseEntity.ok(targetUsers);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User userPayload) {
        return new ResponseEntity<>(
                userService.createUser(
                    userPayload.getName(),
                    userPayload.getRecipes()
                ), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<User> changeUserName(@PathVariable UUID id, @RequestParam String name) {
        User user = userService.changeUserName(id, name);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/recipes/add/")
    public ResponseEntity<User> addUserRecipe(@PathVariable UUID id, @RequestBody Recipe recipe) {
        User user = userService.addUserRecipe(id, recipe);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/recipes/delete/")
    public ResponseEntity<User> deleteUserRecipe(@PathVariable UUID id, @RequestBody Recipe recipe) {
        User user = userService.deleteUserRecipe(id, recipe);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    
}
