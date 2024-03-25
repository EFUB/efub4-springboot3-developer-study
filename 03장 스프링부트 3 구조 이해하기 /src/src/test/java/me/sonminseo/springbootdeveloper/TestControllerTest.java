package me.sonminseo.springbootdeveloper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트 생성 (메인클래스 즉 @SpringBootApplication이 있는 클래스를 찾고, 그 클래스에 포함된 빈을 찾고, 테스트용 애플리케이션 컨텍스트를 생성)
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성 (MockMvc: 서버 배포 없이 테스트용 MVC 환경을 만들 수 있는 클래스. 컨트롤러 테스트에 사용)
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp() { //MockMvc를 설정해주는 메서드
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach // 테스트 실행 후 실행하는 메서드
    public void cleanUp() { // member 테이블에 있는 데이터들을 모두 삭제해주는 역할
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception { // 로직을 실제로 테스트하는 코드. given when then 패턴
        // given: 멤버를 저장한다
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));

        // when: 멤버 리스트를 조회하는 api를 호출한다
        final ResultActions result = mockMvc.perform(get(url) // 1: 요청을 전송
                .accept(MediaType.APPLICATION_JSON)); // 2: 응답받을 타입을 결정 (지금은 JSON)

        // then: 응답코드가 200 OK이고, 반환값중에 0번째의 id와 name을 확인한다
        result
                .andExpect(status().isOk()) // 응답을 검증 (.isOk: 응답 코드가 200 OK 인지 검증)
                .andExpect(jsonPath("$[0].id").value(savedMember.getId())) // 받은 응답에서 값을 가져오는 역할
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}