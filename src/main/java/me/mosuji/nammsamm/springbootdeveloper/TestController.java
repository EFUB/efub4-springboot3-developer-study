package me.mosuji.nammsamm.springbootdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test") // http://localhost:8080/test 로 요청(GET)을 보내면 화면에 Hello World! 출력.
    public String test(){
        return "Hello World!";
    }
}
