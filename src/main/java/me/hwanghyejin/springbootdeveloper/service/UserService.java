package me.hwanghyejin.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hwanghyejin.springbootdeveloper.domain.User;
import me.hwanghyejin.springbootdeveloper.dto.AddUserRequest;
import me.hwanghyejin.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // 패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
