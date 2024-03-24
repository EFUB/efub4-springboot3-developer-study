## 4.1 테스트 코드 개념 익히기
테스트코드: 작성한 코드가 의도대로 잘 동작하고 예상치 못한 문제가 없는지 확인할 목적으로 작성하는 코드

### 4.1.1 테스트 코드란?
- 테스트 코드는 test 디렉터리에서 작업
- 테스트 코드 패턴: given-when-then 패턴
    - 테스트 코드를 아래 세 단계로 구분해 작성하는 방식
    - given: 테스트 실행을 준비하는 단계
    - when: 테스트를 진행하는 단계
    - then: 테스트 결과를 검증하는 단계
- 예제
    ```java
  @DisplayName("새로운 메뉴 저장")
  @Test
  public void saveMenuTest(){
    // given: 메뉴를 저장하기 위한 준비 과정
    final String name = "아메리카노";
    final int price = 2000;
    
    final Menu americano = new Menu(name,price);
    
    //when: 실제로 메뉴를 저장
    final long saveId = menuService.save(americano);
  
    //then: 메뉴가 잘 추가되었는지 검증
    final Menu savedMenu = menuService.findById(saveId).get();
    assertThat(savedMenu.getName()).isEqualTo(name);
    assertThat(savedMenu.getPrice()).isEqualTo(price);
  }
  ```

## 4.2 스프링 부트 3와 테스트
- spring-boot-starter-test 에 테스트를 위한 도구가 모여있다.
  - ```JUnit```: 자바 프로그래밍 언어용 단위 테스트 프레임워크
  - ```Spring Test & Spring Boot Test```: 스프링 부트 어플리케이션을 위한 통합 테스트 지원
  - ```AssertJ```: 검증문인 어설션을 작성하는 데 사용되는 라이브러리
  - ```Hamcresst```: 표현식을 이해하기 쉽게 만드는 데 사용되는 Matcher 라이브러리
  - ```Mockito```: 테스트에 사용할 가짜 객체인 목 객체를 쉽게 만들고, 관리하고, 검증할 수 있게 지원하는 테스트 프레임워크
  - ```JSONassert```: JSON용 어설션 라이브러리
  - ```JsonPath```: JSON 데이터에서 특정 데이터를 선택하고 검색하기 위한 라이브러리

### 4.2.1 JUnit 이란?
- 자바 언어를 위한 단위 테스트 프레임워크
- 단위 테스트: 작성한 코드가 의도대로 작동하는지 작은 단위로 검증하는 것 (단위는 보통 메서드)
- 테스트 방식을 구분할 수 있는 어노테이션 제공
- @Test 어노테이션으로 메서드를 호출할 때마다 새 인스턴스를 생성, 독립 테스트 가능
- 예상 결과를 검증하는 어설션 메서드 제공
- 사용 방법이 단순, 테스트 코드 작성 시간이 적다.
- 자동 실행, 자체 결과를 확인하고 즉각적인 피드백을 제공

  #### Juit으로 단위 테스트 코드 만들기
  ```java
  public class JUnitTest {
    @DisplayName("1 + 2는 3이다") //테스트 이름
    @Test //테스트 메서드
    public void junitTest(){
        int a = 1;
        int b = 2;
        int sum = 3;

        Assertions.assertEquals(sum, a+b); //값이 같은지 확인
    }
  }
  ```
  - @DisplayName
    : 테스트 이름 명시
  
  - @Test
    : 어노테이션을 붙이면 해당 메서드는 테스트를 수행하는 메서드가 됨   
  
  - JUnit은 테스트를 실행할 때마다 테스트를 위한 실행 객체를 만들고, 테스트가 종료되면 실행 객체를 삭제한다. → 테스트끼리 영향을 주지 않도록 하기 위함
  - assertEquals(): 첫번째 인수에는 기대하는 값, 두번째 인수에는 실제로 검증할 값을 넣는다.
  
  #### 자주 사용하는 JUnit 어노테이션
  - @BeforeAll
    : 전체 테스트를 실행하기 전 처음으로 한번만 실행.
      데이터베이스를 연결해야하거나 테스트 환경을 초기화할 때 사용   
      전체 테스트 실행 주기에서 한번만 호출되어야 하므로 메서드를 static으로 선언

  - @BeforeEach
    : 테스트 케이스를 시작하기 전 매번 실행 
  
  - @AfterAll
    : 전체 테스트를 마치고 종료하기 전 한 번만 실행   
      테스트 이후 특정 데이터를 삭제해야하는 경우 사용   
      메서드는 static으로 선언
  
  - @AfterEach
    : 각 테스트 케이스를 종료하기 전 매번 실행 
  
  #### AssertJ로 검증문 가독성 높이기
  - AssertJ: JUnit과 함께 사용해 검증문의 가독성을 확 높여주는 라이브러리
  ```assertThat(a+b).isEqualTo(sum);```
  - 앞서 작성한 Assertion과 비교했을 때, 기댓값과 비굣값이 잘 구분되며 그 의미가 명확하게 읽힌다.

## 4.3 제대로 테스트 코드 작성해보기

```java
@SpringBootTest  //테스트용 어플리케이션 컨텍스트 생성
@AutoConfigureMockMvc //MockMvc 생성 및 자동 구성
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach  //테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach  //테스트 실행 후 실행하는 메서드
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{
      //given: 멤버를 저장한다.
      final String url = "/test";
      Member savedMember = memberRepository.save(new Member(1L, "홍길동"));
  
      //when: 멤버 리스트를 조회하는 API를 호출한다.
      final ResultActions result = mockMvc.perform(get(url)  //[1]
              .accept(MediaType.APPLICATION_JSON));   //[2]
  
      //then: 응답 코드가 200 OK이고, 반환받은 값 중에 0번째 요소의 id와 name이 저장된 값과 같은지 확인한다.
      result
              .andExpect(status().isOk())  //[3]
              //[4] 응답의 0번째 값이 DB에 저장한 값과 같은지 확인
              .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
              .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
  
  }
```
#### 테스트 코드 어노테이션

- @SpringBootTest
: 메인 어플리케이션 클래스에 추가하는 어노테이션인 @SpringBootApplication이 있는 클래스를 찾고, 그 클래스에 포함되어 있는 빈을 찾은 다음 테스트용 어플리케이션 컨텍스트라는 것을 만든다.

- @AutoConfigureMockMvc
: MockMvc를 생성하고 자동으로 구성하는 어노테이션이다.   
  MockMvc란 어플리케이션을 서버에 배포하지 않고도 테스트용 MVC 환경을 만들어 요청 및 전송, 응답 기능을 제공하는 유틸리티 클래스이다. (즉, 컨트롤러를 테스트할 때 사용되는 클래스)

#### 테스트 코드 설명
[1] <b>perform()</b> 메서드는 요청을 전송하는 역할을 하는 메서드로, 결과로 ResultActions 객체를 받는다.    
    <b>ResultActions</b> 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공한다.<br>
[2] <b>accept()</b> 메서드는 요청을 보낼 때 무슨 타입으로 응답을 받을지 결정하는 메서드이다.<br>
[3] <b>andExpect()</b> 메서드는 응답을 검증한다. <br>
[4] <b>jsonPath("$[0].${필드명}")</b>은 JSON 응답값의 값을 가져오는 역할을 하는 메서드이다.<br>

#### HTTP 주요 응답 코드

| 코드                        | 매핑 메서드                  | 설명                                         |
|---------------------------|-------------------------|--------------------------------------------|
| 200 OK                    | isOK()                  | HTTP 응답 코드가 200 OK인지 검증                    |
| 201 Created               | isCreated()             | HTTP 응답 코드가 201 Created인지 검증               |
| 400 Bad Request           | isBadRequest()          | HTTP 응답 코드가 400 Bad Request인지 검증           |
| 403 Forbidden             | isForbidden()           | HTTP 응답 코드가 403 Forbidden인지 검증             |
| 404 Not Found             | isNotFound()            | HTTP 응답 코드가 404 Not Found인지 검증             |
| 400번대 응답코드                | is4xxClientError()      | HTTP 응답 코드가 400번대 응답 코드인지 검증               |
| 500 Internal Server Error | inInternalServerError() | HTTP 응답 코드가 500 Internal Server Error인지 검증 |
| 500번대 응답코드                | is5xxServerError()      | HTTP 응답 코드가 500번대 응답 코드인지 검증               |
