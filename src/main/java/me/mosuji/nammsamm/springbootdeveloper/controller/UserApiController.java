package me.mosuji.nammsamm.springbootdeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.domain.User;
import me.mosuji.nammsamm.springbootdeveloper.dto.AddUserRequest;
import me.mosuji.nammsamm.springbootdeveloper.dto.UserResponse;
import me.mosuji.nammsamm.springbootdeveloper.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // 수정하기 : Controller -> RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    /*
    @PostMapping("/user")
    // 수정하기 : AddUserRequest request -> @RequestBody AddUserRequest request
    public UserResponse signup(@RequestBody  AddUserRequest request){
        Long userId = userService.save(request); // 회원 가입 메서드 호출
        User user = userService.findUserById(userId);
        return UserResponse.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

     */
}
