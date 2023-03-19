package io.sunflower.nutrition.controller;

import io.sunflower.nutrition.dto.NutritionDto;
import io.sunflower.nutrition.service.NutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionService nutritionService;

    @GetMapping("/search-nutritions")
    public List<NutritionDto> searchNutritions(@RequestParam String keyword) {
        return nutritionService.searchNutritions(keyword);
    }

    // 포스팅 내에서 영양성분 조회 시
    @GetMapping("/api/posts/search-nutrition")
    public NutritionDto searchNutrition(@RequestParam String keyword) {
        return nutritionService.searchNutritionInPosting(keyword);
    }
}




