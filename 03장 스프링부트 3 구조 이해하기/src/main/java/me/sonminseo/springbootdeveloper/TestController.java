package me.sonminseo.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 라우터 역할 즉, HTTP 요청과 메서드를 연결하는 장치
// RestController 애너테이션은 Component 애너테이션을 포함하고 있다. -> Component 애너테이션이 하는 역할이 뭐였지?? 빈으로 등록하는거?
public class TestController {
/*    @GetMapping("/test") // /test GET 요청이 오면 test()메서드를 실행
    public String test() {
        return "Hello, world! 우왕";
    }*/

    @Autowired // 빈 주입
    TestService testService;

    /*    @GetMapping("/test")
        public List<Member> getAllMembers(){
            List<Member> members = testService.getAllMembers();
            return members;
        }*/
    @GetMapping("/test")
    public List<Member> getAllMembers() {
        List<Member> members = testService.getAllMembers();
        return members;
    }
}
