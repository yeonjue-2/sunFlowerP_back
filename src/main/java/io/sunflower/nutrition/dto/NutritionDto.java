package io.sunflower.nutrition.dto;

import io.sunflower.nutrition.entity.Nutrition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
public class NutritionDto {
    private String foodName;
    private String foodCode;
    private String nuCarbs;
    private String nuProtein;
    private String nuFat;
    private String nuKcal;

    public NutritionDto(JSONObject nutriJson) {
        this.foodName = nutriJson.getString("DESC_KOR");
        this.foodCode = nutriJson.getString("FOOD_CD");
        this.nuCarbs = nutriJson.getString("NUTR_CONT2");
        this.nuProtein = nutriJson.getString("NUTR_CONT3");
        this.nuFat = nutriJson.getString("NUTR_CONT4");
        this.nuKcal = nutriJson.getString("NUTR_CONT1");
    }

    public NutritionDto(Nutrition nutrition) {
        this.foodName = nutrition.getFoodName();
        this.foodCode = nutrition.getFoodCode();
        this.nuCarbs = nutrition.getNuCarbs();
        this.nuProtein = nutrition.getNuProtein();
        this.nuFat = nutrition.getNuFat();
        this.nuKcal = nutrition.getNuKcal();
    }
}
