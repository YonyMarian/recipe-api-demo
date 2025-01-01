package com.yonymarian.recipe_api_demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aot.hint.annotation.Reflective;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "author")
    private Set<Recipe> recipes;



}
