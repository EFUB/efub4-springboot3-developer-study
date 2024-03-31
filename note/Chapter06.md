## 6.1 사전지식: API와 REST API
네트워크에서의 API: 프로그램 간에 상호작용하기 위한 매개체

### 6.1.1 식당으로 알아보는 API
- 예시: 식당에서 요리를 주문하는 상황
  - 식당에 들어가 손님(클라이언트)이 점원(API)에게 요리를 주문
  - 점원(API)이 주방의 요리사(서버)에게 '요리를 만들어달라'고 요청
  - 요리가 완성되면 점원(API)이 손님(클라이언트)에게 요리를 전달

- 예시: 웹사이트에 방문하는 상황
  - 우리는 웹사이트의 주소를 입력해 '구글의 메인화면을 보여달라'고 요청
  - API가 요청을 받아 서버에게 전달
  - 서버는 API가 준 요청을 처리해 결과물을 만들고 이것을 API에게 전달
  - API는 최종 결과물을 브라우저에게 주고, 우리는 화면을 볼 수 있게 됨

- API의 <b>역할</b>: 클라이언트의 요청을 서버에 전달하고, 서버의 결과물을 클라이언트에게 잘 돌려줌

### 6.1.2 웹의 장점을 최대한 활용하는 REST API
- REST (Respresentational State Transfer) API: 자원을 이름으로 구분해 자원의 상태를 주고받는 API 방식 → URL의 설계 방식
- RESTful API: REST하게 디자인한 API

- REST API의 특징
  - 서버/클라이언트 구조
  - 무상태
  - 캐시 처리 가능
  - 계층화
  - 인터페이스 일관성

- REST API의 장점과 단점
  - (+) URL만 보고도 무슨 행동을 하는 API인지 명확히 파악 가능
  - (+) 무상태이기 때문에 클라이언트와 서버의 역할이 명확히 분리됨
  - (+) HTTP 표준을 사용하는 모든 플랫폼에서 사용 가능
  - (-) HTTP 메서드 개수에 제한이 있고, 공식적으로 적용되는 설계 표준 규악이 없음

#### REST API를 사용하는 방법
- 규칙1: URL에는 동사를 쓰지말고, 자원을 표시해야한다.
  - 자원: 가져오는 데이터
  - 예시:  ```/articles/1```

- 규칙2: 동사는 HTTP 메서드로
  - HTTP 메서드: 서버에 요청하는 방법을 나눈 것.
  - CRUD: POST(create)/GET(read)/PUT(update)/DELETE(delete)
  - 예시: 블로그 글을 추가하는 API ```POST/articles```   
    → 이때 POST는 URL에 입력하는 값이 아니라 내부적으로 처리하는 방식을 미리 정하는 것
<br>

## 6.2 블로그 개발을 위한 엔티티 구성하기

### 6.2.2 엔티티 구성하기
1. domain/Article.java
    ```java
    @Entity
    @NoArgsConstructor
    @Getter
    public class Article {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false)
        private Long id;
    
        @Column(name = "title", nullable = false)
        private String title;
    
        @Column(name = "content", nullable = false)
        private String content;
    
        @Builder  //빌더 패턴으로 객체 생성
        public Article(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
    ```
- <b>@Builder</b> : 롬복에서 지원하는 어노테이션. 생성자 위에 입력하면 빌더 패턴 방식으로 객체 생성이 가능해진다.
  - 빌더 패턴: 객체를 유연하고 직관적으로 생성할 수 있도록 하는 디자인 패턴. 어떤 필드에 어떤 값이 들어가는지 명시적으로 파악 가능해진다.
    ```java
    //빌더 패턴 사용 X
    new Article("abc","def");
    ```
    ```java
    //빌더 패턴 사용 O → 가독성 향상
    Article.builder()
    .title("abc")
    .content("def")
    .build();
    ```

- <b>@Getter</b> : 클래스에 대해 별도의 코드 없이 모든 필드에 대한 접근자 메서드(getter) 를 만들 수 있도록 하는 어노테이션
- <b>@NoArgsConstructor</b> : 접근제어자가 protected인 기본 생성자를 별도의 코드 없이 생성해주는 어노테이션
  - _Entity에 proteted 기본 생성자가 필요한 이유_: JPA가 데이터를 DB에서 조회한 뒤 객체를 생성할 때 Java Reflection을 이용하기 때문. Reflection은 클래스의 이름만 알아도 생성자,필드,메서드 등 클래스의 모든 정보에 접근 가능하지만, 생성자의 매개변수 정보에는 접근이 불가하다. 때문에 Reflection은 기본 생성자로 객체를 생성하고 필드 값을 강제로 매핑해주는 방식을 사용한다. 따라서 JPA를 이용할 때 엔티티에 기본 생성자가 없다면 객체 생성 자체가 실패하기 때문에 기본 생성자를 반드시 생성해줄 것을 정해놓고 있는 것. + 아무 곳에서나 객체가 생성되지 않도록 접근 지정자를 protected로 설정. JPA의 프록시 기술을 사용할 때 hibernate가 객체를 강제로 만들어야하는 경우가 있으므로 private으로는 설정이 불가하다. ([ref](https://velog.io/@jyyoun1022/JPA-JPA%EC%97%90%EC%84%9C-Entity%EC%97%90-protected-%EC%83%9D%EC%84%B1%EC%9E%90%EB%A5%BC-%EB%A7%8C%EB%93%9C%EB%8A%94-%EC%9D%B4%EC%9C%A0))

### 6.2.3 리포지터리 만들기
- repository/BlogRepository.java 인터페이스 생성
<br>

## 6.3 블로그 글 작성을 위한 API 구현하기

### 6.3.1 서비스 메서드 코드 작성하기
- **dto/AddArticleRequest.java**
  - DTO(Data Transfer Object): 계층끼리 데이터를 교환하기 위해 사용하는 객체. 별도의 비즈니스 로직을 포함하지 않는다.

- **service/BlogService.java**
  - `@RequiredArgsConstructor` : 빈을 생성자로 생성하는 롬복에서 지원하는 어노테이션. _final_ 키워드나 @_NotNull_ 이 붙은 필드로 생성자를 만들어준다.
  - `@Service` : 해당 클래스를 빈으로 서블릿 컨테이너에 등록해주는 어노테이션
  - `save()` : JpaRepository의 부모 클래스인 CrudRepository 에 선언된 메서드로, 사용 시 데이터베이스에 엔티티를 저장

### 6.3.2 컨트롤러 메서드 코드 작성하기
- **controller/BlogApiController.java**
  - `@RestController` : HTTP 응답으로 객체 데이터를 JSON 형식으로 반환
  - `@PostMapping()` : HTTP 메서드가 POST일 때 요청받은 URL과 동일한 메서드와 매핑시킴
  - `@RequestBody` : HTTP를 요청할 때 본문에 해당하는 값을 @RequestBody 어노테이션이 붙은 대상 객체에 매핑
  - `ResponseEntity.status()`, `body()` : 응답 코드로 201 Created 를 응답하고, 테이블에 저장된 객체를 반환

### 6.3.3 API 실행 테스트하기

- H2 콘솔 활성화
  - application.yml 파일 수정
- 스프링 부트 서버 실행 후 포스트맨을 통한 요청
- H2 콘솔을 통해 데이터베이스에 잘 저장됐는지 확인 (localhost:8080/h2-console)

### 6.3.4 반복 작업을 줄여 줄 테스트 코드 작성하기
```java
@SpringBootTest // 테스트용 어플리케이션 컨텍스트
@AutoConfigureMockMvc ///MockMvc 생성 및 자동 구성
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;  //직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach  //테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title,content);

        //객체를 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); //크기가 1인지 검출
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

}
```

- ObjectMapper: Jackson 라이브러리에서 제공하는 클래스. 이 클래스로 만든 객체는 자바 객체를 JSON 데이터로 변환하는 직렬화, 또는 반대로 JSON 데이터를 자바에서 사용하기 위해 자바 객체로 변환하는 역직렬화 할 때 사용된다.
  - `writeValueAsString()` : 객체를 JSON 으로 직렬화할 때 사용한 메서드

- 직렬화와 역직렬화
  - 직렬화: 자바 시스템 내부에서 사용되는 객체를 외부에서 사용하도록 데이터를 변환하는 작업. ex. Article 객체를 JSON 형식으로 직렬화
  - 역직렬화: 직렬화의 반대. 외부에서 사용하는 데이터를 자바의 객체 형태로 변환하는 작업. ex. JSON 형식의 값을 자바 객체에 맞게 변환하는 것

- MockMvc 를 사용해 HTTP 메서드, URL, 요청 본문, 요청 타입 등을 설정한 뒤 설정 내용을 바탕으로 테스트 요청을 보낼 수 있다.
