package io.sunflower.post.entity;

import io.sunflower.post.dto.PostRequest;
import io.sunflower.common.Timestamped;
import io.sunflower.user.entity.User;
import io.sunflower.common.enumeration.MealCountEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Post extends Timestamped {

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

//    @Column(nullable = false)
//    private List<postImage> postImages = new ArrayList<>();
//

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

//    public void update(PostUpdateRequest request) {
//        this.postContents = request.getPostContents();
//        this.menuList = request.getMenuList();
//        this.mealCount = request.getMealCount();
//        this.nuCarbs = request.getNuCarbs();
//        this.nuProtein = request.getNuProtein();
//        this.nuFat = request.getNuFat();
//        this.nuKcal = request.getNuKcal();
//    }

    public void update(List<PostRequest> postRequests) {
        for (int i = 0; i < postRequests.size(); i++) {
            if (postRequests.get(i).getPostContents() != null) {
                this.setPostContents(postRequests.get(i).getPostContents());
            }
            if (postRequests.get(i).getMealCount() != null) {
                this.setMealCount(postRequests.get(i).getMealCount());
            }
            if (postRequests.get(i).getMenuList() != null) {
                this.setMenuList(postRequests.get(i).getMenuList());
            }
            if (postRequests.get(i).getNuCarbs() != null) {
                this.setNuCarbs(postRequests.get(i).getNuCarbs());
            }
            if (postRequests.get(i).getNuFat() != null) {
                this.setNuFat(postRequests.get(i).getNuFat());
            }
            if (postRequests.get(i).getNuProtein() != null) {
                this.setNuProtein(postRequests.get(i).getNuProtein());
            }
            if (postRequests.get(i).getNuKcal() != null) {
                this.setNuKcal(postRequests.get(i).getNuKcal());
            }
        }
    }

}
