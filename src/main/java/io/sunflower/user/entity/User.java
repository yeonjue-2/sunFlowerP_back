package io.sunflower.user.entity;

import io.sunflower.profile.dto.ProfileUpdateRequest;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.common.model.Timestamped;
import io.sunflower.entity.enumeration.UserGenderEnum;
import io.sunflower.entity.enumeration.UserRoleEnum;
import io.sunflower.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
@DynamicUpdate
public class User extends Timestamped {

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

    @ColumnDefault("'만나서 반갑습니다. 오늘 내가 택한 식단입니다.'")
    private String userContents;

//    @Column(nullable = false)
//    private String userImage;

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isMember;

//    @Column(nullable = false)
//    private boolean isActivated;

    @ColumnDefault("null")
    private Long kakaoId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

//    @OneToMany(mappedBy = "users")
//    private List<Comment> commentList = new ArrayList<>();


    @Builder
    public User(SignupRequest request, String emailId, String password, String nickname, UserRoleEnum role) {
        this.emailId = emailId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.gender = request.getGender();
        this.userContents = request.getUserContents();
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


    public void updateProfile(List<ProfileUpdateRequest> updateRequests) {
        for (int i = 0; i < updateRequests.size(); i++) {
            if (updateRequests.get(i).getNickname() != null) {
                this.setNickname(updateRequests.get(i).getNickname());
            }
            if (updateRequests.get(i).getUserContents() != null) {
                this.setUserContents(updateRequests.get(i).getUserContents());
            }
            if (updateRequests.get(i).getGender() != null) {
                this.setGender(updateRequests.get(i).getGender());
            }
        }
    }
}
