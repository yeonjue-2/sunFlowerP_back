package io.sunflower.user.dto;

import io.sunflower.common.enumeration.UserGenderEnum;
import io.sunflower.post.dto.PostDetailResponse;
import io.sunflower.post.dto.PostResponse;
import io.sunflower.user.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserProfileResponse {
    private String nickname;
    private String userContents;
    private UserGenderEnum gender;
    private String userImageUrl;
    private List<PostResponse> posts;

    public UserProfileResponse(User user) {
        this.nickname = user.getNickname();
        this.userContents = user.getUserContents();
        this.gender = user.getGender();
        this.userImageUrl = user.getUserImageUrl();
        this.posts = user.getPosts().stream()
                .map(PostResponse::new).collect(Collectors.toList());
    }
}

