package io.sunflower.entity;

import io.sunflower.dto.request.SignupRequest;
import io.sunflower.entity.enumeration.UserGenderEnum;
import io.sunflower.entity.enumeration.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class User extends Timestamped{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String personalId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UserGenderEnum gender;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

//    @OneToMany(mappedBy = "users")
//    private List<Comment> commentList = new ArrayList<>();



    public User(SignupRequest request, String personalId, String password, String nickname, String email, UserRoleEnum role) {
        this.personalId = personalId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.gender = request.getGender();
        this.userContents = request.getUserContents();
        this.isMember = request.isMember();
    }




}
