package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;


}
