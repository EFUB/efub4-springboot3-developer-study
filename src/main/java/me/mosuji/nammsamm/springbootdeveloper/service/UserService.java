package me.mosuji.nammsamm.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.domain.User;
import me.mosuji.nammsamm.springbootdeveloper.dto.AddUserRequest;
import me.mosuji.nammsamm.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // password 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
