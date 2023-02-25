package io.sunflower.post.dto;

import io.sunflower.post.entity.Post;
import io.sunflower.user.entity.User;
import io.sunflower.common.enumeration.MealCountEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {
    private Long postId;
    private String postContents;
    private String menuList;
    private MealCountEnum mealCount;
    private String nuCarbs;
    private String nuProtein;
    private String nuFat;
    private String nuKcal;
    private String nickname;
    private List<String> postImageUrls;
    private LocalDateTime createAt;


    public PostResponse(Post post, User user) {
        this.postId = post.getId();
        this.postContents = post.getPostContents();
        this.menuList = post.getMenuList();
        this.mealCount = post.getMealCount();
        this.nuCarbs = post.getNuCarbs();
        this.nuProtein = post.getNuProtein();
        this.nuFat = post.getNuFat();
        this.nuKcal = post.getNuKcal();
        this.createAt = post.getCreatedAt();
        this.nickname = user.getNickname();
        postImageUrls = post.getPostImages().stream()
                .map(i -> i.getPostImageUrl())
                .collect(Collectors.toList());
    }

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.postContents = post.getPostContents();
        this.menuList = post.getMenuList();
        this.mealCount = post.getMealCount();
        this.nuCarbs = post.getNuCarbs();
        this.nuProtein = post.getNuProtein();
        this.nuFat = post.getNuFat();
        this.nuKcal = post.getNuKcal();
        this.createAt = post.getCreatedAt();
        this.nickname = post.getUser().getNickname();
        postImageUrls = post.getPostImages().stream()
                .map(i -> i.getPostImageUrl())
                .collect(Collectors.toList());
    }
}
