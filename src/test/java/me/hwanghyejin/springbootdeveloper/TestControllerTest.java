package me.hwanghyejin.springbootdeveloper;

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

@SpringBootTest  // 테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc  // MockMvc 생성 및 자동 구성
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach  // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){  // mockMvcSetUp() 메서드를 실행해 MockMvc를 설정
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach  // 테스트 실행 후 실행하는 메서드
    public void CleanUp(){  // CleanUp() 메서드를 실행해 member 테이블에 있는 데이터들을 모두 삭제
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{
        //given
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }

}