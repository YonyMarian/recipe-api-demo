package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;


}
