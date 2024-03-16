package me.hwanghyejin.springbootdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")   // /test GET요청이 오면 test() 메서드 실행
    public String test(){
        return "Hello, world!";
    }
}