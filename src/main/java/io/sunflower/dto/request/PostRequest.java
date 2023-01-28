package io.sunflower.dto.request;

import io.sunflower.entity.Post;
import io.sunflower.entity.User;
import io.sunflower.entity.enumeration.MealCountEnum;
import lombok.*;

import java.util.List;

@Getter
public class PostRequest {

    private String postContents;
    private String menuList;
    private MealCountEnum mealCount;
    private String nuCarbs;
    private String nuProtein;
    private String nuFat;
    private String nuKcal;
//    private List<postImage> postImages = new ArrayList<>();

    public Post toEntity(User user) {
        return Post.builder()
                .postContents(this.getPostContents())
                .menuList(this.getMenuList())
                .mealCount(this.getMealCount())
                .nuCarbs(this.getNuCarbs())
                .nuProtein(this.getNuProtein())
                .nuFat(this.getNuFat())
                .nuKcal(this.nuKcal)
                .user(user)
                .build();
    }
}
