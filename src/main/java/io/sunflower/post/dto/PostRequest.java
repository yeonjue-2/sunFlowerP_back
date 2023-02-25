package io.sunflower.post.dto;

import io.sunflower.post.entity.Post;
import io.sunflower.user.entity.User;
import io.sunflower.common.enumeration.MealCountEnum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
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

    public Post toEntity(User user) {
        return Post.builder()
                .postContents(this.getPostContents())
                .menuList(this.getMenuList())
                .mealCount(this.getMealCount())
                .nuCarbs(this.getNuCarbs())
                .nuProtein(this.getNuProtein())
                .nuFat(this.getNuFat())
                .nuKcal(this.getNuKcal())
                .user(user)
                .build();
    }
}
