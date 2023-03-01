package io.sunflower.nutrition;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class NutritionController {

    private final NutritionService nutritionService;

    @GetMapping("/search-nutritions")
    public List<NutritionDto> searchNutritions(@RequestParam String keyword) {
        return nutritionService.searchNutritions(keyword);
    }

    // 영양성분 단건 조회
    @GetMapping("/posts/search-nutrition")
    public NutritionDto searchNutrition(@RequestParam String keyword) {
        return nutritionService.searchNutritions(keyword).get(0);
    }
}




