package io.sunflower;

import io.sunflower.nutrition.dto.NutritionDto;
import io.sunflower.nutrition.service.NutritionService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NutritionTest {

    @Test
    void searchNutrition() {
        //given
        NutritionService nutritionService = new NutritionService();

        //when
        List<NutritionDto> nutritionDtoList = nutritionService.searchNutritions("닭갈비");

        //then
        assertThat(nutritionDtoList).isNotEmpty();
    }
}
