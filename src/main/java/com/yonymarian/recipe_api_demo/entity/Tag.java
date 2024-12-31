package com.yonymarian.recipe_api_demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.ColumnTransformer;

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
    @ColumnTransformer(read = "UPPER(name)", forColumn = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Recipe> recipes;
}
