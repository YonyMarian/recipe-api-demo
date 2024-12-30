package com.yonymarian.recipe_api_demo.repository;

import com.yonymarian.recipe_api_demo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    public List<Tag> findByName(String name);
}
