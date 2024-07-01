//package me.gkdudans.springbootdeveloper.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import me.gkdudans.springbootdeveloper.config.jwt.JwtFactory;
//import me.gkdudans.springbootdeveloper.config.jwt.JwtProperties;
//import me.gkdudans.springbootdeveloper.domain.RefreshToken;
//import me.gkdudans.springbootdeveloper.domain.User;
//import me.gkdudans.springbootdeveloper.dto.CreateAccessTokenRequest;
//import me.gkdudans.springbootdeveloper.repository.RefreshTokenRepository;
//import me.gkdudans.springbootdeveloper.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TokenApiControllerTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected ObjectMapper objectMapper;
//    @Autowired
//    private WebApplicationContext context;
//    @Autowired
//    JwtProperties jwtProperties;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RefreshTokenRepository refreshTokenRepository;
//
//    @BeforeEach
//    public void mockMvcSetUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .build();
//        userRepository.deleteAll();
//    }
//
//    @DisplayName("createNewAccessToken: 새로운 액세스 토큰을 발급한다.")
//    @Test
//    public void createNewAccessToken() throws Exception {
//        // given: 테스트 유저 생성, 리프레시 토큰 생성 -> 요청 객체 생성
//        final String url = "/api/token";
//
//        User testUser = userRepository.save(User.builder()
//                .email("user@gmail.com")
//                .password("test")
//                .build());
//
//        String refreshToken = JwtFactory.builder()
//                .claims(Map.of("id", testUser.getId()))
//                .build()
//                .createToken(jwtProperties);
//
//        System.out.println("Refresh Token: " + refreshToken);
//        refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToken));
//
//        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
//        request.setRefreshToken(refreshToken);
//        final String requestBody = objectMapper.writeValueAsString(request);
//        System.out.println("RequestBody: " + requestBody);
//
//        // when: 토큰 추가 API에 요청을 보냄, given절의 객체를 요청 본문으로 함께 보냄
//        ResultActions resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody));
//
//        // then: 응답 코드 및 엑세스 토큰 확인
//        resultActions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.accessToken").isNotEmpty());
//    }
//
//}
