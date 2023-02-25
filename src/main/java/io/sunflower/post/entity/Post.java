package io.sunflower.post.entity;

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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Post extends TimeStamped {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = false)
    private String menuList;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Builder
    public Post(String postContents, String menuList, MealCountEnum mealCount, String nuCarbs,
                String nuProtein, String nuFat, String nuKcal,User user) {
        this.postContents = postContents;
        this.menuList = menuList;
        this.mealCount = mealCount;
        this.nuCarbs = nuCarbs;
        this.nuProtein = nuProtein;
        this.nuFat = nuFat;
        this.nuKcal = nuKcal;
        this.user = user;
    }


//    public void update(List<PostRequest> postRequests) {
//        for (int i = 0; i < postRequests.size(); i++) {
//            if (postRequests.get(i).getPostContents() != null) {
//                this.setPostContents(postRequests.get(i).getPostContents());
//            }
//            if (postRequests.get(i).getMealCount() != null) {
//                this.setMealCount(postRequests.get(i).getMealCount());
//            }
//            if (postRequests.get(i).getMenuList() != null) {
//                this.setMenuList(postRequests.get(i).getMenuList());
//            }
//            if (postRequests.get(i).getNuCarbs() != null) {
//                this.setNuCarbs(postRequests.get(i).getNuCarbs());
//            }
//            if (postRequests.get(i).getNuFat() != null) {
//                this.setNuFat(postRequests.get(i).getNuFat());
//            }
//            if (postRequests.get(i).getNuProtein() != null) {
//                this.setNuProtein(postRequests.get(i).getNuProtein());
//            }
//            if (postRequests.get(i).getNuKcal() != null) {
//                this.setNuKcal(postRequests.get(i).getNuKcal());
//            }
//        }
//    }

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

    // 연관 관계 편의 메소드
    public void addUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void addPostImage(PostImage postImage) {
        postImages.add(postImage);
        postImage.addPost(this);
    }

}
