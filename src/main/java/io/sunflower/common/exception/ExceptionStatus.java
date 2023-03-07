package io.sunflower.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {

    // 400 ->  BAD _ REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
    IMAGE_UPLOAD_FAILED(400, "이미지 업로드에 실패하였습니다."),
    UNSUPPORTED_IMAGE_TYPE(400, "지원하지 않는 이미지 형식입니다."),
    TO_MUCH_FILES(400, " 이미지는 최대 4개만 업로드 할 수 있습니다."),

    // 404 ->  NOT _ FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 값)
    NOT_FOUND_POST(404, "게시물이 존재하지 않습니다."),
    NOT_FOUND_USER(404, "사용자가 존재하지 않습니다."),
    NOT_FOUND_COMMENT(404, "댓글이 존재하지 않습니다."),
    NOT_FOUND_LIKE(404, "해당 게시물에 좋아요를 누르지 않았습니다."),

    // 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
    DUPLICATED_USER(409, "이미 등록된 사용자 입니다."),
    DUPLICATED_EMAIL(409, "이미 등록된 이메일 입니다."),
    DUPLICATED_NICKNAME(409, "이미 사용중인 닉네임 입니다."),
    DUPLICATED_LIKE(409, "이미 좋아요를 눌렀습니다."),

    // 500 -> INTERNAL SERVER ERROR : 서버에러
    FAIL_TO_POSTING(500, "게시글 작성을 잠시 후 시도해주세요"),

    // 401 -> AUTH
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_ACCESS_TOKEN(401, "유효하지 않은 액세스 토큰입니다."),
    INVALID_EMAIL_OR_PW(400, "잘못된 아이디 혹은 비밀번호 입력입니다."),
    NOT_VERIFIED_EMAIL(401, "인증되지 않은 이메일입니다."),
    INVALID_CODE(401, "인증번호가 일치하지 않습니다."),

    NOT_AUTHORIZED_COMMENT(401, "해당 댓글의 작성자가 아닙니다."),
    NOT_AUTHORIZED_POST(401, "해당 게시글의 작성자가 아닙니다."),
    NOT_AUTHORIZED_USER(401, "해당 사용자만 접근 가능합니다.");

    private final int statusCode;
    private final String message;

}
