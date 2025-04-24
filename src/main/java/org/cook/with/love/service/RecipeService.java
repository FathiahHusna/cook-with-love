package org.cook.with.love.service;

import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.dto.RecipeDTO;
import org.cook.with.love.entity.RecipeEntity;
import org.cook.with.love.enums.RecipeErrorCode;
import org.cook.with.love.exception.InvalidRecipeException;
import org.cook.with.love.mapper.RecipeMapper;
import org.cook.with.love.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class RecipeService {

    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    @Autowired
    private RecipeRepository recipeRepository;

    public List<RecipeDTO> getRecipe(){
        log.trace("[getRecipe] Start getRecipe");
        List<RecipeEntity> recipeEntityList = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOList = recipeEntityList.stream().map(r -> RecipeMapper.INSTANCE.recipeEntityToRecipeDto(r)).toList();
        int listSize = !recipeDTOList.isEmpty() ? recipeDTOList.size() : 0;
        log.info("[getRecipe] Check recipe list: {}" , listSize);
        return recipeDTOList;
    }

    public RecipeDTO addRecipe(RecipeDTO reqRecipeDTO){
        try{
            log.trace("[addRecipe] Start addRecipe");
            validateInput(reqRecipeDTO, "addRecipe");
            RecipeEntity recipeEntity = RecipeMapper.INSTANCE.recipeDtoToRecipeEntity(reqRecipeDTO);
            RecipeEntity savedRecipeEntity = recipeRepository.saveAndFlush(recipeEntity);
            log.info("[addRecipe] Check saved recipe: [{}]" , savedRecipeEntity);

            RecipeDTO resRecipeDto = RecipeMapper.INSTANCE.recipeEntityToRecipeDto(savedRecipeEntity);
            log.info("[addRecipe] Check return recipe: [{}]" , resRecipeDto);
            return resRecipeDto;
        }catch (InvalidRecipeException ex){
            log.error("[addRecipe] Error in addRecipe: " , ex);
            return setInvalidEx(ex);
        } catch (Exception ex){
            log.error("[addRecipe] Error in addRecipe: " , ex);
            return setUnexpectedEx();
        }finally{
            log.trace("[addRecipe] End addRecipe");
        }
    }

    public RecipeDTO getRecipeById(String recipeId){
        try{
            log.trace("[getRecipeById] Start getRecipeById [{}]", recipeId);
            if(!StringUtils.hasText(recipeId)){
                throw new InvalidRecipeException(RecipeErrorCode.RECIPE_FIELD_EMPTY.getCode(), RecipeErrorCode.RECIPE_FIELD_EMPTY.getMessage());
            }
            Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(UUID.fromString(recipeId));
            if(recipeEntityOptional.isPresent()){
                RecipeDTO resRecipeDTO = RecipeMapper.INSTANCE.recipeEntityToRecipeDto(recipeEntityOptional.get());
                log.trace("[getRecipeById] Fetch getRecipeById [{}]", resRecipeDTO);
                return resRecipeDTO;
            }else{
                return setUnknownRecipe();
            }
        }catch (InvalidRecipeException ex){
            log.error("[getRecipeById] Error in getRecipeById: " , ex);
            return setInvalidEx(ex);
        }catch (Exception ex){
            log.error("[getRecipeById] Error in getRecipeById: " , ex);
            return setUnexpectedEx();
        }finally {
            log.trace("[getRecipeById] End getRecipeById");
        }
    }

    public RecipeDTO updateRecipe(RecipeDTO reqRecipeDTO){
        try{
            log.trace("[updateRecipe] Start updateRecipe");
            validateInput(reqRecipeDTO, "updateRecipe");
            Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(UUID.fromString(reqRecipeDTO.getId()));
            if(recipeEntityOptional.isPresent()){
                RecipeEntity recipeEntity = recipeEntityOptional.get();
                checkAndUpdateValue(recipeEntity, reqRecipeDTO);
                RecipeEntity savedRecipeEntity = recipeRepository.saveAndFlush(recipeEntity);
                RecipeDTO resRecipeDTO = RecipeMapper.INSTANCE.recipeEntityToRecipeDto(savedRecipeEntity);
                log.trace("[updateRecipe] Updated recipe [{}]", resRecipeDTO);
                return resRecipeDTO;
            }else{
                return setUnknownRecipe();
            }
        }catch (InvalidRecipeException ex){
            log.error("[updateRecipe] Error in updateRecipe: " , ex);
            return setInvalidEx(ex);
        }catch (Exception ex){
            log.error("[updateRecipe] Error in updateRecipe: " , ex);
            return setUnexpectedEx();
        }finally {
            log.trace("[updateRecipe] End updateRecipe");
        }
    }

    private RecipeEntity checkAndUpdateValue(RecipeEntity recipeEntity, RecipeDTO reqRecipeDTO){
        if(!Objects.equals(reqRecipeDTO.getName(), recipeEntity.getName())){
            recipeEntity.setName(reqRecipeDTO.getName());
        }
        if(!Objects.equals(reqRecipeDTO.getIngredients(), recipeEntity.getIngredients())){
            recipeEntity.setIngredients(reqRecipeDTO.getIngredients());
        }
        if(!Objects.equals(reqRecipeDTO.getStep(), recipeEntity.getStep())){
            recipeEntity.setStep(reqRecipeDTO.getStep());
        }
        if(!Objects.equals(reqRecipeDTO.getReference(), recipeEntity.getReference())){
            recipeEntity.setReference(reqRecipeDTO.getReference());
        }
        if(!Objects.equals(reqRecipeDTO.getTags(), recipeEntity.getTags())){
            recipeEntity.setTags(reqRecipeDTO.getTags());
        }
        if(!Objects.equals(reqRecipeDTO.getCategory(), recipeEntity.getCategory())){
            recipeEntity.setCategory(reqRecipeDTO.getCategory());
        }
        return recipeEntity;
    }

    private void validateInput(RecipeDTO recipeDTO, String serviceName){
        log.trace("[validateInput] Start validateInput [{}], [{}]", serviceName, recipeDTO);
        if(recipeDTO!=null){
            if(!StringUtils.hasText(recipeDTO.getName())){
                throw new InvalidRecipeException(RecipeErrorCode.RECIPE_FIELD_EMPTY.getCode(), RecipeErrorCode.RECIPE_FIELD_EMPTY.getMessage());
            }
            if(!StringUtils.hasText(recipeDTO.getIngredients())){
                throw new InvalidRecipeException(RecipeErrorCode.RECIPE_FIELD_EMPTY.getCode(), RecipeErrorCode.RECIPE_FIELD_EMPTY.getMessage());
            }
            if(!StringUtils.hasText(recipeDTO.getStep())){
                throw new InvalidRecipeException(RecipeErrorCode.RECIPE_FIELD_EMPTY.getCode(), RecipeErrorCode.RECIPE_FIELD_EMPTY.getMessage());
            }
        }else{
            throw new InvalidRecipeException(RecipeErrorCode.RECIPE_FIELD_EMPTY.getCode(), RecipeErrorCode.RECIPE_FIELD_EMPTY.getMessage());
        }
    }

    private RecipeDTO setInvalidEx(InvalidRecipeException ex){
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setErrorCode(ex.getErrorCode());
        recipeDTO.setErrorMessage(ex.getMessage());
        return recipeDTO;
    }

    private RecipeDTO setUnexpectedEx(){
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setErrorCode(RecipeErrorCode.SERVICES_DOWN.getCode());
        recipeDTO.setErrorMessage(RecipeErrorCode.SERVICES_DOWN.getMessage());
        return recipeDTO;
    }

    private RecipeDTO setUnknownRecipe(){
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setErrorCode(RecipeErrorCode.RECIPE_NOT_FOUND.getCode());
        recipeDTO.setErrorMessage(RecipeErrorCode.RECIPE_NOT_FOUND.getMessage());
        return recipeDTO;
    }
}
