package io.sunflower.nutrition;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NutritionService {
    private static final String KEY_ID = "651efec27e044ae5b36f";
    private static final String URL = "http://openapi.foodsafetykorea.go.kr/api/" + KEY_ID + "/I2790/json/1/5/DESC_KOR={keyword}";

    public List<NutritionDto> searchNutrition(String keyword) {
        RestTemplate rest = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("x-api-key", "651efec27e044ae5b36f");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body);
        ResponseEntity<String> responseEntity = rest.exchange(URL, HttpMethod.GET, requestEntity, String.class, keyword);

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        log.info("NAVER API Status Code : " + status);

        String response = responseEntity.getBody();

        return fromJSONtoItems(response);
    }

    public List<NutritionDto> fromJSONtoItems(String response) {

        JSONObject rjson = new JSONObject(response);
        JSONArray items  = rjson.getJSONArray("items");
        List<NutritionDto> nutritionDtos = new ArrayList<>();

        for (int i=0; i<items.length(); i++) {
            JSONObject nutriJson = items.getJSONObject(i);
            NutritionDto nutritionDto = new NutritionDto(nutriJson);
            nutritionDtos.add(nutritionDto);
        }

        return nutritionDtos;
    }
}
