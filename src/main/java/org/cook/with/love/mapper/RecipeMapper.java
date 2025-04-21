package org.cook.with.love.mapper;

import org.cook.with.love.dto.RecipeDTO;
import org.cook.with.love.entity.RecipeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper( RecipeMapper.class );

    RecipeDTO recipeEntityToRecipeDto(RecipeEntity recipeEntity);

    RecipeEntity recipeDtoToRecipeEntity(RecipeDTO recipeDTO);
}
