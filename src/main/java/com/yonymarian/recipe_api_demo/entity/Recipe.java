package com.yonymarian.recipe_api_demo.entity;

import com.yonymarian.recipe_api_demo.utils.Difficulty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NonNull
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private String author;

    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Column(name = "total_time")
    private Integer totalTime;

    @NonNull
    @ElementCollection
    @CollectionTable(
            name = "ingredients",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @MapKeyColumn(name = "ingredient_name")
    @Column(name = "ingredient_amount")
    private Map<String, String> ingredients;

    @NonNull
    @ElementCollection
    @CollectionTable(
            name = "steps",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @OrderColumn(name = "step_number")
    @Column(name = "step_text")
    private List<String> steps;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
