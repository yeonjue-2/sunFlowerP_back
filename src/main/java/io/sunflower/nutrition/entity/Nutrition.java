package io.sunflower.nutrition.entity;


import io.sunflower.nutrition.dto.NutritionDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String foodCode;

    @ColumnDefault("0")
    private String nuCarbs;

    @ColumnDefault("0")
    private String nuProtein;

    @ColumnDefault("0")
    private String nuFat;

    @Column(nullable = false)
    private String nuKcal;

    public Nutrition(NutritionDto nutritionDto, String keyword) {
        this.foodName = keyword;
        this.foodCode = nutritionDto.getFoodCode();
        this.nuCarbs = nutritionDto.getNuCarbs();
        this.nuProtein = nutritionDto.getNuProtein();
        this.nuFat = nutritionDto.getNuFat();
        this.nuKcal = nutritionDto.getNuKcal();
    }
}
