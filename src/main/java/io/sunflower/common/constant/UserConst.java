package io.sunflower.common.constant;

public class UserConst {
    public static final String PASSWORD_REGEXP = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,14}";
    public static final String PASSWORD_CONDITION = "비밀번호는 영문자, 특수문자, 숫자를 반드시 포함하여야 합니다.";
    public static final String NECESSARY_PASSWORD = "비밀번호는 필수 입력정보입니다.";
    public static final String PASSWORD_LENGTH_CONDITION = "비밀번호는 8 ~ 14자로 설정해 주세요.";
    public static final String DEFAULT_USER_IMAGE = "6b6528a3-2396-4126-acfa-8e8f06397a57.png";

}
