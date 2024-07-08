package me.mosuji.nammsamm.springbootdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String password;
}
