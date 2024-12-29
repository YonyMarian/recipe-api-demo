package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

}
