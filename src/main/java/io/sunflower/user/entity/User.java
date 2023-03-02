package io.sunflower.user.entity;

import io.sunflower.common.enumeration.UserStatus;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.common.TimeStamped;
import io.sunflower.common.enumeration.UserGenderEnum;
import io.sunflower.common.enumeration.UserRoleEnum;
import io.sunflower.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
@DynamicInsert
public class User extends TimeStamped {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String emailId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @ColumnDefault("'NONE'")
    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

    @ColumnDefault("'만나서 반갑습니다. 오늘 내가 택한 식단을 알려드리겠습니다.'")
    private String userContents;

    @Column
    private String userImageUrl;

    @ColumnDefault("null")
    private Long kakaoId;

    @ColumnDefault("'ACTIVATED'")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(SignupRequest request, String emailId, String password, String nickname, UserRoleEnum role, String userImageUrl) {
        this.emailId = emailId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.gender = request.getGender();
        this.userContents = request.getUserContents();
        this.userImageUrl = userImageUrl;
    }

    @Builder
    public User(Long id, String kakaoEmail, String password, String kakaoNickname, UserRoleEnum role) {
        this.kakaoId = id;
        this.emailId = kakaoEmail;
        this.password = password;
        this.nickname = kakaoNickname;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateUserInfo(UserInfoUpdateRequest request, String password, String userImageUrl) {

        if (password != null) {
            this.setPassword(password);
        }

        if (request.getNickname() != null) {
            this.setNickname(request.getNickname());
        }

        if (request.getUserContents() != null) {
            this.setUserContents(request.getUserContents());
        }

        if (request.getGender() != null) {
            this.setGender(request.getGender());
        }

        this.setUserImageUrl(userImageUrl);

    }

    public void addPost(Post post) {
        posts.add(post);
//        post.addUser(this);
    }
}
