package com.yonymarian.recipe_api_demo.controller;


import com.yonymarian.recipe_api_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    
}
