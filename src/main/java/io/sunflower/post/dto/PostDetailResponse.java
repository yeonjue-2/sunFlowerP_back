package io.sunflower.post.dto;

import io.sunflower.comment.dto.CommentResponse;
import io.sunflower.comment.entity.Comment;
import io.sunflower.post.entity.Post;
import io.sunflower.post.entity.PostImage;
import io.sunflower.user.entity.User;
import io.sunflower.common.enumeration.MealCountEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponse {
    private Long postId;
    private String postContents;
    private String menuList;
    private MealCountEnum mealCount;
    private String nuCarbs;
    private String nuProtein;
    private String nuFat;
    private String nuKcal;
    private String nickname;
    private String userImageUrl;
    private List<String> postImageUrls;
//    private List<CommentResponse> comments;
    private int likeCount;
    private LocalDateTime createAt;


    public PostDetailResponse(Post post, User user) {
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
        this.userImageUrl = user.getUserImageUrl();
        postImageUrls = post.getPostImages().stream()
                .map(PostImage::getPostImageUrl)
                .collect(Collectors.toList());
    }

    public PostDetailResponse(Post post) {
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
        this.userImageUrl = post.getUser().getUserImageUrl();
        postImageUrls = post.getPostImages().stream()
                .map(PostImage::getPostImageUrl)
                .collect(Collectors.toList());
//        comments = post.getComments().stream()
//                .sorted(Comparator.comparing(Comment::getId).reversed())
//                .map(CommentResponse::of)
//                .collect(Collectors.toList());
        this.likeCount = post.getLikeCount();
    }
}
