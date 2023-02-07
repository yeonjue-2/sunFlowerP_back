package io.sunflower.nutrition;

import io.sunflower.nutrition.NutritionDto;
import io.sunflower.nutrition.NutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NutritionController {

    private final NutritionService nutritionService;

    @GetMapping("/search")
    public List<NutritionDto> searchNutritions(@RequestParam String keyword) {
        return nutritionService.searchNutritions(keyword);
    }

//    @GetMapping("/search")
//    public NutritionDto searchNutrition(@RequestParam String keyword) {
//        return nutritionService.searchNutritions(keyword).get(0);
//    }
}




