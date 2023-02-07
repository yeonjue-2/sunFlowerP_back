package io.sunflower.entity;

import io.sunflower.dto.request.SignupRequest;
import io.sunflower.entity.enumeration.UserGenderEnum;
import io.sunflower.entity.enumeration.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class User extends Timestamped{

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

    @ColumnDefault("'만나서 반갑습니다. 오늘 내가 택한 식단입니다.'")
    private String userContents;

    @Column
    private String kakaoId;

//    @Column(nullable = false)
//    private String userImage;

    @Column(nullable = false)
    private boolean isMember;

//    @Column(nullable = false)
//    private boolean isActivated;

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
        this.isMember = request.isMember();
    }




}
