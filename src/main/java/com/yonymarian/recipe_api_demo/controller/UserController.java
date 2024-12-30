package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.entity.Recipe;
import com.yonymarian.recipe_api_demo.entity.User;
import com.yonymarian.recipe_api_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User targetUser = userService.getUserById(id);
        return ResponseEntity.ok(targetUser);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<User>> getUsersByName(@PathVariable String name) {
        List<User> targetUsers = userService.getUsersByName(name);
        return ResponseEntity.ok(targetUsers);
    }

    @GetMapping("/{recipe}")
    public ResponseEntity<List<User>> getUsersByRecipeMade(@RequestParam Recipe recipe) {
        List<User> targetUsers = userService.getUsersByRecipeMade(recipe);
        return ResponseEntity.ok(targetUsers);
    }

    @PostMapping("/create/{user-payload}")
    public ResponseEntity<User> createUser(@RequestBody User userPayload) {
        return new ResponseEntity<>(
                userService.createUser(
                    userPayload.getName(),
                    userPayload.getRecipes()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}/update/{name}")
    public ResponseEntity<User> changeUserName(@PathVariable UUID id, @RequestParam String name) {
        User user = userService.changeUserName(id, name);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/recipes/add/{recipe}")
    public ResponseEntity<User> addUserRecipe(@PathVariable UUID id, @RequestParam Recipe recipe) {
        User user = userService.addUserRecipe(id, recipe);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/recipes/delete/{recipe}")
    public ResponseEntity<User> deleteUserRecipe(@PathVariable UUID id, @RequestParam Recipe recipe) {
        User user = userService.deleteUserRecipe(id, recipe);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    
}
