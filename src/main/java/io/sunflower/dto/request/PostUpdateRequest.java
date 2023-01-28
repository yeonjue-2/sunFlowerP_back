package io.sunflower.dto.request;

import io.sunflower.entity.enumeration.MealCountEnum;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    private String postContents;
    private String menuList;
    private MealCountEnum mealCount;
    private String nuCarbs;
    private String nuProtein;
    private String nuFat;
    private String nuKcal;
}
