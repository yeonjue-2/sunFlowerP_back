package io.sunflower.dto.request;

import io.sunflower.entity.enumeration.MealCountEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private String postContents;
    private String menuList;
    private MealCountEnum mealCount;
    private double nuCarbs;
    private double nuProtein;
    private double nuFat;
    private double nuKcal;
//    private List<postImage> postImages = new ArrayList<>();
}
