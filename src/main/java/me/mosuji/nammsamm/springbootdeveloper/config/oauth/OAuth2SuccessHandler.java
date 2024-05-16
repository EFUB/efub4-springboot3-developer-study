package me.mosuji.nammsamm.springbootdeveloper.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.config.jwt.TokenProvider;
import me.mosuji.nammsamm.springbootdeveloper.domain.RefreshToken;
import me.mosuji.nammsamm.springbootdeveloper.domain.User;
import me.mosuji.nammsamm.springbootdeveloper.repository.RefreshTokenRepository;
import me.mosuji.nammsamm.springbootdeveloper.service.UserService;
import me.mosuji.nammsamm.springbootdeveloper.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final  OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestBasedOnCookieRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        // 1.리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        /* 토큰 제공자를 사용해 리프레시 토큰을 만든 뒤 saveRefreshToken() 메서드를 호출;해 해당 리프레시 토큰을 DB에 유저 아이디와 함께 저장함.
           이후에는 클라이언트에서 액세스 토큰이 만료되면 재발급 요청하도록 addRefreshTokenToCookie() 메서드를 호출해 쿠키에 리프레시 토큰을 저장.
         */
        String refreshToken = tokenProvider.genarateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        // 2. 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.genarateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        // 3. 인증 관련 설정값, 쿠키 제거
        /* 인증 프로세스를 진행하면서 세션과 쿠키에 임시로 저장해둔 인증 관련 데이터를 제거.
         */
        clearAuthenticationAttributes(request, response);

        // 4. 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    // 생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken){
        int cooKieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME); // 이 부분은 왜 필요하지?
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken,cooKieMaxAge);
    }

    // 인증 관련 설정값, 쿠키 제거 : 이 부분은 왜 필요한가?
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
        authorizationRequestBasedOnCookieRepository.removeAuthorizationRequestCookies(request, response);
    }

    // 액세스 토큰을 패스에 추가
    private String getTargetUrl(String token){
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}

