package org.cook.with.love.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecipeErrorCode {
    RECIPE_FIELD_EMPTY("RECIPE_001", "Value cannot be null or empty"),
    INVALID_CATEGORY("RECIPE_002", "Recipe category is invalid"),
    SERVICES_DOWN("RECIPE_003", "Recipe service is unavailable"),
    RECIPE_NOT_FOUND("RECIPE_004", "No recipe found")

    ;

    private final String code;
    private final String message;
}
