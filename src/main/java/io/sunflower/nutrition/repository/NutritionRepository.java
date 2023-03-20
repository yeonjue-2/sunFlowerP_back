package io.sunflower.nutrition.repository;

import io.sunflower.nutrition.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    boolean existsByFoodNameContaining(String keyword);
    Nutrition findNutritionByFoodNameContaining(String keyword);
}
