package com.yonymarian.recipe_api_demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "recipe")
    private Set<Recipe> recipes;
}
