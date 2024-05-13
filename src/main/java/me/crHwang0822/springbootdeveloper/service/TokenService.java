package me.crHwang0822.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.crHwang0822.springbootdeveloper.config.jwt.TokenProvider;
import me.crHwang0822.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        //토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        //유효한 리프레시 토큰으로 사용자 ID 찾기
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        //사용자 ID로 사용자 찾기
        User user = userService.findById(userId);
        //새로운 액세스 토큰 생성 후 반환
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
