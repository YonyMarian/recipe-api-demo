package com.yonymarian.recipe_api_demo.utils;

public enum Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    NONE_PROVIDED;

    public String getDifficulty() {
        return this.name();
    }
}
