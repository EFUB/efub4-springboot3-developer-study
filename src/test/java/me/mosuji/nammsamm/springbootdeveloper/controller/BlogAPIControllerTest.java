package me.mosuji.nammsamm.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.mosuji.nammsamm.springbootdeveloper.domain.Article;
import me.mosuji.nammsamm.springbootdeveloper.domain.User;
import me.mosuji.nammsamm.springbootdeveloper.dto.AddArticleRequest;
import me.mosuji.nammsamm.springbootdeveloper.dto.UpdateArticleRequest;
import me.mosuji.nammsamm.springbootdeveloper.repository.BlogRepository;
import me.mosuji.nammsamm.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class BlogAPIControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역질렬화를 위한 클래스

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @BeforeEach
    void setSecurityContext(){
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        SecurityContext context1 = SecurityContextHolder.getContext();
        context1.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(), user.getAuthorities()));
    }

    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception{
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체 JSON 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");


        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다")
    @Test
    public void findAllArticles() throws Exception{
        // given : 블로그 글을 저장한다
        final String url = "/api/articles";
        Article savedArticle = createDefaultArticle();

        //final String title = "title";
        //final String content = "content";

        //blogRepository.save(Article.builder()
                //.title(title)
               // .content(content)
               // .build());

        // when : 목록 조회 API 를 호출한다.
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then : 응답 코드가 200 ok 이고, 반환값은 값 중에 0 번째 요소의 content 와 title 이 저장된 값과 같은지 확인한다.
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));

    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception{
        // given : 블로그 글을 저장한다.
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();
        //final String title = "title";
        //final String content = "content";

        //Article savedArticle = blogRepository.save(Article.builder()
               // .title(title)
               // .content(content)
               // .build());

        // when : 저장한 블로그 글의 id 값으로 API 를 호출한다.
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then : 응답 코드가 200 OK 이고 반환받은 content 와 title 이 저장된 값과 같은지 확인한다.
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception{
        // given : 블로그 글을 저장한다.
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();
       // final String title = "title";
        //final String content = "content";

        //Article savedArticle = blogRepository.save(Article.builder()
            //    .title(title)
            //    .content(content)
            //    .build());

        // when : 저장한 블로그 글의 id 값으로 삭제 API 를 호출한다.
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then : 응답 코드가 200 OK 이고, 블로그 글 리스트를 전체 조회해 배열 크가기 0인지 확인한다.
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle : 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception{
        // given : 블로그 글을 저장하고, 블로그 글 수정에 필요한 모든 요청 객체를 만든다.
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();
        //final String title = "title";
        //final String content = "content";

        //Article savedArticle = blogRepository.save(Article.builder()
              //  .title(title)
              //  .content(content)
              //  .build());

        final String newTitle = "newTitle";
        final String newContent = "newContent";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when : UPDATE API 로 수정 요청을 보냄.
        // 묘청 타입 : json, given 절에서 만들어놓은 객체를 요청 본문으로 함께 보냄.
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then : 응답 코드가 200 OK 인지 확인함. 블로그 글 id로 조회한 후에 값이 수정되었는지 확인함.
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    private Article createDefaultArticle(){
        return blogRepository.save(Article.builder()
                .title("title")
                .author(user.getUsername())
                .content("content")
                .build());
    }

}