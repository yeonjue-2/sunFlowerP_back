package io.sunflower.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sunflower.auth.dto.LoginResponse;
import io.sunflower.common.enumeration.UserRoleEnum;
import io.sunflower.kakao.dto.KakaoUserInfo;
import io.sunflower.security.jwt.JwtTokenProvider;
import io.sunflower.security.jwt.dto.TokenDto;
import io.sunflower.user.entity.User;
import io.sunflower.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

import static io.sunflower.common.constant.KakaoConstant.CLIENT_ID;
import static io.sunflower.common.constant.KakaoConstant.REDIRECT_URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse kakaoLogin(String code) throws IOException {
        // 1. 인가코드로 엑세스 토큰 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. JWT 토큰 반환
        Authentication authentication = jwtTokenProvider.createAuthentication(kakaoUser.getEmailId());
        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);

        return LoginResponse.of(kakaoUser.getEmailId(), tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    // 1. "code"로 "accessToken" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();   // json 객체를 파싱해서 객체로 담아와서 사용
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();     // 키 값 가져와서 타입 변환
    }

    // 2. 토큰으로 카카오 API 호출 : "accessToken"으로 "카카오 사용자 정보" 가져오기
    private KakaoUserInfo getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);      // 키, 밸류
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfo(id, email, nickname);
    }

    // 3. 필요시에 회원가입
    private User registerKakaoUserIfNeeded(KakaoUserInfo kakaoUserInfo) {
        // DB 에 중복된 KakaoId 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoUser == null) {
            // 카카오로 가입한 사용자는 아니지만 카카오 사용자 email과 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getKakaoEmail();
            User sameEmailUser = userRepository.findByEmailId(kakaoEmail).orElse(null);
            // 카카오로 가입은 안했지만 유저 정보가 있을 때, 기존 회원정보에 카카오 Id 추가
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 아무 정보도 없다면, 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getKakaoEmail();

                kakaoUser = new User(kakaoId, email, encodedPassword, kakaoUserInfo.getKakaoNickname(), UserRoleEnum.USER);
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    // 1. 카카오로 가입한 적 없는 경우 -> 일반 가입했지만 카카오와 이메일이 같은 경우  -> 카카오로 로그인 가능하게 변경
    //                          -> 일반 가입했고 카카오와 이메일이 다른 경우
    // 2. 카카오로 가입한 경우

//    // 5. response에 kakaoUser 정보를 담아 클라이언트에 전송
//    private void addToResponse(User kakaoUser, HttpServletResponse response) throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//
//        KakaoUserResponse kakaoUserResponse = KakaoUserResponse.builder()
//                .emailId(kakaoUser.getEmailId())
//                .password(kakaoUser.getPassword())
//                .nickname(kakaoUser.getNickname())
//                .userContents(kakaoUser.getUserContents())
//                .gender(kakaoUser.getGender())
//                .build();
//
//        String result = objectMapper.writeValueAsString(kakaoUser);
//
//        response.getWriter().write(result);
//    }

}
