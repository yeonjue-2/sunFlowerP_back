package io.sunflower.post.dto;

import io.sunflower.common.enumeration.MealCountEnum;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PostRequest {

    @Length(max=255)
    private String postContents;

    private String menuList;
    private MealCountEnum mealCount;
    private String nuCarbs;
    private String nuProtein;
    private String nuFat;
    private String nuKcal;

    @Size(max=4, message = "* 이미지는 최대 4장까지 업로드 가능합니다.")
    private List<MultipartFile> files;
}
