package io.sunflower.dto.response;

import io.sunflower.entity.Post;
import io.sunflower.entity.enumeration.MealCountEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private String postContents;
    private String menuList;
    private MealCountEnum mealCount;
    private double nuCarbs;
    private double nuProtein;
    private double nuFat;
    private double nuKcal;
    private LocalDateTime createAt;

    public PostResponse(Post post) {
        this.postContents = post.getPostContents();
        this.menuList = post.getMenuList();
        this.mealCount = post.getMealCount();
        this.nuCarbs = post.getNuCarbs();
        this.nuProtein = post.getNuProtein();
        this.nuFat = post.getNuFat();
        this.nuKcal = post.getNuKcal();
        this.createAt = post.getCreatedAt();
    }
}
