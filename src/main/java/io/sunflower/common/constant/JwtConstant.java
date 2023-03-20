package io.sunflower.common.constant;

public class JwtConstant {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;   // 60 * 1000L = 1분
    public static final long REFRESH_TOKEN_TIME = 60 * 60 * 1000L * 24;
}
