package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByName();
    public Optional<User> findByRecipes_Name();
}
