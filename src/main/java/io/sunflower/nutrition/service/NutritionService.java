package io.sunflower.nutrition.service;

import io.sunflower.nutrition.dto.NutritionDto;
import io.sunflower.nutrition.entity.Nutrition;
import io.sunflower.nutrition.repository.NutritionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NutritionService {
    private static final String KEY_ID = "651efec27e044ae5b36f";
    private static final String URL = "http://openapi.foodsafetykorea.go.kr/api/" + KEY_ID + "/I2790/json/1/5/DESC_KOR={keyword}";

    private final NutritionRepository nutritionRepository;

    /**
     * 칼로리 검색에서 사용, 영양성분 정보가 db에 저장되지 않음
     * @param keyword
     * @return
     */
    public List<NutritionDto> searchNutritions(String keyword) {
        RestTemplate rest = new RestTemplate();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body);
        ResponseEntity<String> responseEntity = rest.exchange(URL, HttpMethod.GET, requestEntity, String.class, keyword);

//        HttpStatus httpStatus = responseEntity.getStatusCode();
//        int status = httpStatus.value();

        String response = responseEntity.getBody();

        return fromJSONtoItems(keyword, response);  // keyword 값으로 data를 저장하기 위해
    }

    public List<NutritionDto> fromJSONtoItems(String keyword, String response) {

        JSONObject rjson = new JSONObject(response).getJSONObject("I2790");
        JSONArray items = rjson.getJSONArray("row");
        List<NutritionDto> nutritionDtos = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject nutriJson = items.getJSONObject(i);
            NutritionDto nutritionDto = new NutritionDto(nutriJson);
            nutritionDtos.add(nutritionDto);
        }

        return nutritionDtos;
    }


    /**
     * 포스팅 내 영양성분 정보 검색에 사용, 결과 정보가 db에 저장됨
     * @param keyword
     * @return
     */
    public NutritionDto searchNutritionInPosting(String keyword) {

        if (nutritionRepository.existsByFoodNameContaining(keyword)) {
            Nutrition nutrition = nutritionRepository.findNutritionByFoodNameContaining(keyword);
            return new NutritionDto(nutrition);
        }

        RestTemplate rest = new RestTemplate();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body);
        ResponseEntity<String> responseEntity = rest.exchange(URL, HttpMethod.GET, requestEntity, String.class, keyword);

        String response = responseEntity.getBody();

        return fromJSONtoItem(keyword, response);  // keyword 값으로 data를 저장하기 위해
    }

    public NutritionDto fromJSONtoItem(String keyword, String response) {

        JSONObject rjson = new JSONObject(response).getJSONObject("I2790");
        JSONArray items = rjson.getJSONArray("row");

        JSONObject nutriJson = items.getJSONObject(0);
        NutritionDto nutritionDto = new NutritionDto(nutriJson);
        Nutrition nutrition = new Nutrition(nutritionDto, keyword);
        nutritionRepository.save(nutrition);

        return nutritionDto;
    }
}
