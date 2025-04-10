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

        List<RecipeDTO> recipeDTOList = recipeEntityList.stream().map(r -> RecipeMapper.INSTANCE.carToCarDto(r)).toList();
        int listSize = !recipeDTOList.isEmpty() ? recipeDTOList.size() : 0;
        log.info("[getRecipe] Check recipe list: {}" , listSize);
        return recipeDTOList;
    }
}
