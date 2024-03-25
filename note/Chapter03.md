## 3.1 스프링 부트 3 구조 살펴보기
- 스프링 부트는 각 계층이 양 옆의 계층과 통신하는 구조를 따른다.   
- 계층: 각자의 역할과 책임이 있는 어떤 소프트웨어의 구성 요소

### 3.1.1 카페와 빵집으로 이해하는 계층
- 각 계층은 자신의 책임에 맞는 역할을 수행하며 서로 필요에 따라 소통하지만, 다른 계층에 직접 간섭하거나 영향을 미치지는 못한다.
- 계층은 개념의 영역, 컨트롤러/서비스/리포지토리는 실제 구현을 위한 영역

    #### 프레젠테이션 계층
    HTTP 요청을 받고 이 요청을 비즈니스 계층으로 전송하는 역할을 한다.   
    컨트롤러가 프레젠테이션 계층의 역할을 한다.

    #### 비즈니스 계층
    모든 비즈니스 로직 (서비스를 만들기 위한 로직) 을 처리한다.   
    서비스가 비즈니스 계층의 역할을 한다.

    #### 퍼시스턴스 계층
    모든 데이터베이스 관련 로직을 처리한다.   
    이 과정에서 데이터베이스에 접근하는 DAO 객체 (데이터베이스 계층과 상호작용 하기 위한 객체) 를 사용할 수도 있다.    
    리포지토리가 퍼시스턴스 계층의 역할을 한다.

### 3.1.2 스프링 부트 프로젝트 디렉토리 구성하며 살펴보기

  #### main
  프로젝트 실행에 필요한 소스 코드나 리소스 파일이 들어있는 공간
  
  #### test
  프로젝트의 소스코드를 테스트할 목적의 코드나 리소스 파일이 들어있는 공간
  
  #### build.gradle
  의존성이나 플러그인 설정 등 빌드에 필요한 설정을 할 때 사용
  
  #### settings.gradle
  빌드할 프로젝트의 정보를 설정하는 파일

### 3.1.3 main 디렉터리 구성하기
- main/resources/templates: HTML과 같은 뷰 관련 파일을 넣을 디렉터리
- main/resources/static: JS,CSS,이미지와 같은 정적 파일을 넣을 디렉터리
- main/resources/application.yml: 스프링 부트 서버가 실행되면 자동으로 로딩되는 설정 파일

## 3.2 스프링 부트 3 프로젝트 발전시키기

### 3.2.1 build.gradle 에 의존성 추가하기
- 스프링 데이터 JPA, 인메모리 데이터베이스 H2, 롬복 라이브러리 추가

### 3.2.2 프레젠테이션, 서비스, 퍼시스턴스 계층 만들기

#### 프레젠테이션 계층: TestController.java
```java
@RestController
public class TestController {
    @Autowired  //TestService 빈 주입
    TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers(){
        List<Member> members = testService.getAllMembers();
        return members;
    }
}
```

#### 비즈니스 계층: TestService.java
```java
@Service
public class TestService {

    @Autowired
    MemberRepository memberRepository;  //빈 주입

    public List<Member> getAllMembers(){
        return memberRepository.findAll();  //멤버 목록 얻기
    }
}
```

#### 퍼시스턴트 계층: Member.java
member라는 이름의 테이블에 접근하는 데 사용할 객체
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;
}
```

#### member 테이블과 Member 클래스 매핑: MemberRepository.java
```java
public interface MemberRepository extends JpaRepository<Member,Long> {
}
```

### 3.2.4 작동 확인하기
- resources/data.sql: 더미 데이터 생성하는 SQL 작성
- resources/application.yml
  - show-sql, format-sql: 어플리케이션 실행 과정에서 쿼리할 일이 있을 시 실행구문을 모두 보여주는 옵션
  - defer-datasource-initialization: 어플리케이션 실행 시 테이블을 생성하고 data.sql 파일의 쿼리를 실행하도록 하는 옵션

## 3.3 스프링 부트 요청-응답 과정 한 방에 이해하기
1. 포스트맨이 톰캣에 /test GET 요청 → 요청이 스프링 부트 내로 이동
2. 스프링 부트의 디스패처 서블릿이 URL을 분석하고, 해당 요청을 처리할 수 있는 컨트롤러(TestController)를 찾고 요청을 전달
3. getAllMembers() 메서드와 요청이 매치되고, 메서드에서는 비즈니스 계층과 퍼시스턴스 계층을 통하면서 필요한 데이터를 가져옴
4. 뷰 리졸버는 템플릿 엔진을 사용해 HTML 문서를 만들거나, JSON/XML 등의 데이터를 생성
5. members 를 리턴하고 해당 데이터를 포스트맨에서 볼 수 있게됨