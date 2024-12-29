package com.yonymarian.recipe_api_demo.entity;

import com.yonymarian.recipe_api_demo.utils.Difficulty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "difficulty")
    private Difficulty difficulty;

    //TODO: ensure minimum of 5 min
    @NonNull
    @Column(name = "total_time")
    private Integer totalTime;

    @NonNull
    @ElementCollection
    @CollectionTable(
            name = "ingredients",
            joinColumns = @JoinColumn(name = "recipe_id", nullable = false)
    )
    @MapKeyColumn(name = "ingredient_name")
    @Column(name = "ingredient_amount", nullable = false)
    private Map<String, String> ingredients;

    @NonNull
    @ElementCollection
    @CollectionTable(
            name = "steps",
            joinColumns = @JoinColumn(name = "recipe_id", nullable = false)
    )
    @OrderColumn(name = "step_number", nullable = false)
    @Column(name = "step_text", nullable = false)
    private List<String> steps;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "tags",
            joinColumns = @JoinColumn(name = "recipe_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false)
    )
    private Set<Tag> tags;
}
