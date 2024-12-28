package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
