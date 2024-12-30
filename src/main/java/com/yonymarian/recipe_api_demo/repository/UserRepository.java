package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    public List<User> findByName(String name);
    public List<User> findByRecipes_Name(String recipeName);
}
