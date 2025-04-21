package org.cook.with.love.service;

import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.dto.RecipeDTO;
import org.cook.with.love.entity.RecipeEntity;
import org.cook.with.love.mapper.RecipeMapper;
import org.cook.with.love.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipeService {

    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    @Autowired
    private RecipeRepository recipeRepository;

    public List<RecipeDTO> getRecipe(){
        List<RecipeEntity> recipeEntityList = recipeRepository.findAll();

        List<RecipeDTO> recipeDTOList = recipeEntityList.stream().map(r -> RecipeMapper.INSTANCE.recipeEntityToRecipeDto(r)).toList();
        int listSize = !recipeDTOList.isEmpty() ? recipeDTOList.size() : 0;
        log.info("[getRecipe] Check recipe list: {}" , listSize);
        return recipeDTOList;
    }

    public RecipeDTO addRecipe(RecipeDTO reqRecipeDTO){
        if(reqRecipeDTO!=null){
            RecipeEntity recipeEntity = RecipeMapper.INSTANCE.recipeDtoToRecipeEntity(reqRecipeDTO);
            RecipeEntity savedRecipeEntity = recipeRepository.saveAndFlush(recipeEntity);
            log.info("[addRecipe] Check saved recipe: [{}]" , savedRecipeEntity);

            RecipeDTO resRecipeDto = RecipeMapper.INSTANCE.recipeEntityToRecipeDto(savedRecipeEntity);
            log.info("[addRecipe] Check return recipe: [{}]" , resRecipeDto);
            return resRecipeDto;
        }
        return null;
    }
}
