package org.cook.with.love.dto;

import lombok.Data;

@Data
public class RecipeDTO extends ErrorDTO{

    private String id;

    private String name; //mandatory

    private String ingredients; //mandatory

    private String step; //mandatory

    private String reference;

    private String tags;

    private String category;

}
