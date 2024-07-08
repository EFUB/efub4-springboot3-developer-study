package me.mosuji.nammsamm.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.domain.RefreshToken;
import me.mosuji.nammsamm.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;


    // 전달받은 리프레시 토큰으로 리프레시 토큰 객체를 검색하여 전달하는 메서드.
    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new IllegalArgumentException("Unexpected token"));
    }
}
