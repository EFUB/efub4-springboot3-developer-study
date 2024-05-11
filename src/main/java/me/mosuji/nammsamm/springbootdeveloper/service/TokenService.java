package me.mosuji.nammsamm.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.config.jwt.TokenProvider;
import me.mosuji.nammsamm.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    // 전달받은 리프레시 토큰으로 토큰 유효성 검사를 진행 -> 유효한 토큰일 때 리프레시 토큰으로 사용자 ID를 찾고, 해당 ID를 통해 사용자를 찾음. -> 이후 새로운 액세스 토큰 생성.
    public String createNewAccessToken(String refreshToken){
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findUserById(userId);

        return tokenProvider.genarateToken(user, Duration.ofHours(2));
    }

}
