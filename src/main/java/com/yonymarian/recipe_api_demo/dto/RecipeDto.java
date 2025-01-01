package com.yonymarian.recipe_api_demo.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class RecipeDto {
    //same fields as Recipe entity, but converted to Strings for ease of data transfer

    private String name;
    private UUID authorId;
    private String difficulty;
    private Integer totalTime;
    private Map<String, String> ingredients;
    private List<String> steps;
    private Set<String> tagNames;
}
