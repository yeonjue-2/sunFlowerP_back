package io.sunflower.nutrition;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NutritionController {

    private final NutritionService nutritionService;

    @GetMapping("/search")
    public List<NutritionDto> searchNutrition(@RequestParam String keyword) {
        return nutritionService.searchNutrition(keyword);
    }
}




