package io.sunflower.post.entity;

import io.sunflower.comment.entity.Comment;
import io.sunflower.post.dto.PostRequest;
import io.sunflower.common.TimeStamped;
import io.sunflower.user.entity.User;
import io.sunflower.common.enumeration.MealCountEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Post extends TimeStamped {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String postContents;

    @Column
    private String menuList;

    @Column
    @Enumerated(EnumType.STRING)
    private MealCountEnum mealCount;

    @ColumnDefault("0")
    @Column(nullable = false)
    private String nuCarbs;

    @ColumnDefault("0")
    @Column(nullable = false)
    private String nuProtein;

    @ColumnDefault("0")
    @Column(nullable = false)
    private String nuFat;

    @Column(nullable = false)
    private String nuKcal;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int likeCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Post(PostRequest request, User user) {
        this.postContents = request.getPostContents();
        this.menuList = request.getMenuList();
        this.mealCount = request.getMealCount();
        this.nuCarbs = request.getNuCarbs();
        this.nuProtein = request.getNuProtein();
        this.nuFat = request.getNuFat();
        this.nuKcal = request.getNuKcal();
        this.user = user;
    }

    public void update(PostRequest request) {
        if (request.getPostContents() != null) {
            this.setPostContents(request.getPostContents());
        }
        if (request.getMealCount() != null) {
            this.setMealCount(request.getMealCount());
        }
        if (request.getMenuList() != null) {
            this.setMenuList(request.getMenuList());
        }
        if (request.getNuCarbs() != null) {
            this.setNuCarbs(request.getNuCarbs());
        }
        if (request.getNuProtein() != null) {
            this.setNuProtein(request.getNuProtein());
        }
        if (request.getNuFat() != null) {
            this.setNuFat(request.getNuFat());
        }
        if (request.getNuKcal() != null) {
            this.setNuKcal(request.getNuKcal());
        }

    }

    public void addPostImage(PostImage postImage) {
        postImages.add(postImage);
        postImage.addPost(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.addPost(this);
    }

    public void updateLike(int likeCount) {
        this.likeCount = likeCount;
    }
}
