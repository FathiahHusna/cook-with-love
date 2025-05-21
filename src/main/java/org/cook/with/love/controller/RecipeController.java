package org.cook.with.love.controller;

import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.dto.RecipeDTO;
import org.cook.with.love.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipe")
    public List<RecipeDTO> getAllRecipe(){
        return recipeService.getRecipe();
    }

    @PostMapping("/recipe")
    public RecipeDTO addRecipe(@RequestBody RecipeDTO recipeDTO){
        return recipeService.addRecipe(recipeDTO);
    }

    @GetMapping("/recipe/{id}")
    public RecipeDTO getRecipeById(@PathVariable("id") String recipeId){
        return recipeService.getRecipeById(recipeId);
    }

    @PutMapping("/recipe")
    public RecipeDTO updateRecipe(@RequestBody RecipeDTO recipeDTO){
        return recipeService.updateRecipe(recipeDTO);
    }

    @DeleteMapping("/recipe/{id}")
    public RecipeDTO deleteRecipe(@PathVariable("id") String recipeId){
        return recipeService.deleteRecipe(recipeId);
    }

    @GetMapping("/recipe/search")
    public List<RecipeDTO> searchRecipes(@RequestParam(name = "searchText", required = false) String searchText, @RequestParam(name = "category", required = false) String category){
        return recipeService.searchRecipe(searchText, category);
    }

    @GetMapping("/recipe/random")
    public RecipeDTO searchRecipeRandom(){
        return recipeService.searchRecipeRandom();
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Test successful";
    }

}
