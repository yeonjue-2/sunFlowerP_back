package io.sunflower.entity;

import io.sunflower.dto.request.PostRequest;
import io.sunflower.dto.request.PostUpdateRequest;
import io.sunflower.entity.enumeration.MealCountEnum;
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
@Entity
@NoArgsConstructor
@DynamicInsert
public class Post extends Timestamped{

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = false)
    private String menuList;

    @Column(nullable = false)
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

    @JoinColumn(name = "user_id", nullable = false)
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

    public void update(PostUpdateRequest request) {
        this.postContents = request.getPostContents();
        this.menuList = request.getMenuList();
        this.mealCount = request.getMealCount();
        this.nuCarbs = request.getNuCarbs();
        this.nuProtein = request.getNuProtein();
        this.nuFat = request.getNuFat();
        this.nuKcal = request.getNuKcal();
    }

}
