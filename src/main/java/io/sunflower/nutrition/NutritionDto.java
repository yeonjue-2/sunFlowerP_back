package io.sunflower.nutrition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Getter
@NoArgsConstructor
public class NutritionDto {
    private String foodName;
    private String foodCode;
    private double nuCarbs;
    private double nuProtein;
    private double nuFat;
    private double nuKcal;

    public NutritionDto(JSONObject nutriJson) {
        this.foodName = nutriJson.getString("DESC_KOR");
        this.foodCode = nutriJson.getString("FOOD_CD");
        this.nuCarbs = nutriJson.getDouble("NUTR_CONT2");
        this.nuProtein = nutriJson.getDouble("NUTR_CONT3");
        this.nuFat = nutriJson.getDouble("NUTR_CONT4");
        this.nuKcal = nutriJson.getDouble("NUTR_CONT1");
    }
}
