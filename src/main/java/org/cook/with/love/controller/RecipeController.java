package org.cook.with.love.controller;

import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.dto.RecipeDTO;
import org.cook.with.love.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes")
    public List<RecipeDTO> getAllRecipe(){
        return recipeService.getRecipe();
    }

    @PostMapping("/add/recipe")
    public RecipeDTO addRecipe(@RequestBody RecipeDTO recipeDTO){
        return recipeService.addRecipe(recipeDTO);
    }
}
