package io.sunflower.entity;

import io.sunflower.dto.request.PostRequest;
import io.sunflower.entity.enumeration.MealCountEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = false)
    private String menuList;

    @Column(nullable = false)
    private MealCountEnum mealCount;

    @Column(nullable = false)
    private double nuCarbs;

    @Column(nullable = false)
    private double nuProtein;

    @Column(nullable = false)
    private double nuFat;

    @Column(nullable = false)
    private double nuKcal;

//    @Column(nullable = false)
//    private List<psotImage> postImages = new ArrayList<>();
//

    @Column(nullable = false)
    private Long userId;

    public Post(PostRequest request) {
        this.postContents = request.getPostContents();
        this.menuList = request.getMenuList();
        this.mealCount = request.getMealCount();
        this.nuCarbs = request.getNuCarbs();
        this.nuProtein = request.getNuProtein();
        this.nuFat = request.getNuFat();
        this.nuKcal = request.getNuKcal();
//        this.userId = userId;
    }


}
