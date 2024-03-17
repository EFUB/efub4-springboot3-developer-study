## 2.1 스프링과 스프링 부트

### 2.1.1 스프링의 등장
- 엔터프라이즈 어플리케이션: 대규모의 복잡한 데이터를 관리하는 어플리케이션. 서버 성능과 안정성, 보안이 중요
- 2003년 6월, 서버 성능과 안정성, 보안을 매우 높은 수준으로 제공하는 도구인 스프링 프레임워크 등장

### 2.1.2 스프링을 더 쉽게 만들어주는 스프링 부트
- 설정이 매우 복잡하다는 스프링의 단점을 보완하고자 출시 (2013/04)
- 스프링 부트: 스프링 프레임워크를 더 쉽고 빠르게 이용할 수 있도록 만들어주는 도구
- 스프링부트의 특징
  - 톰캣, 제티, 언더토우 같은 WAS 가 내장되어 있어서 따로 설치하지 않아도 독립적으로 실행 가능
  - 빌드 구성을 단순화하는 스프링 부트 스타터를 제공
  - XML 설정을 하지 않고 자바 코드로 모두 작성 가능
  - JAR 을 이용해서 자바 옵션만으로도 배포 가능
  - 어플리케이션의 모니터링 및 관리 도구인 스프링 액츄에이터 제공

#### 스프링과 스프링 부트의 차이점
1. 구성의 차이   
    - 스프링: 어플리케이션 개발에 필요한 환경을 수동으로 구성 및 정의   
    - 스프링 부트: 스프링 코어와 스프링 MVC의 모든 기능을 자동으로 로드
2. 내장 WAS 의 유무
    스프링 부트의 경우 jar 파일만 만들면, 별도로 WAS 를 설정하지 않아도 어플리케이션 실행 가능

## 2.2 스프링 콘셉트 공부하기

### 2.2.1 제어의 역전과 의존성 주입
스프링은 모든 기능의 기반을 제어의 역전(IOC)와 의존성 주입(DI)에 두고 있다.   

#### IOC란?
- Inversion of Control 을 줄인 표현
- 다른 객체를 직접 생성하거나 제어하는 것이 아니라 외부에서 관리하는 객체를 가져와 사용하는 것  
- 스프링은 스프링 컨테이너가 객체를 관리, 제공하는 역할을 한다.

- 클래스 A 에서 클래스 B 객체를 생성하는 예
    ```java
    public class A {
        b = new B();
    }
    ```
- 스프링 컨테이너가 객체를 관리하는 방식 예
    ```java
    public class A {
        private B b;
    }
  ```

#### DI란?
- Dependency Injection 을 줄인 표현으로, IoC를 구현하기 위해 사용하는 방법이 DI
- 어떤 클래스가 다른 클래스에 의존한다는 뜻
- @Autowired: 스프링 컨테이너에 있는 빈이라는 것을 주입하는 역할
- 빈: 스프링 컨테이너에서 관리하는 객체   

- 스프링 컨테이너에서 객체를 주입받는 모습 예
    ```java
    public class A {
        @Autowired
        B b;
    } 
  ```

### 2.2.2 빈과 스프링 컨테이너

#### 스프링 컨테이너란?
빈이 생성되고 소멸되기까지의 생명 주기를 관리하는 주체
#### 빈이란?
- 스프링 컨테이너가 생성하고 관리하는 객체
- 스프링은 빈을 스프링 컨테이너에 등록하기 위해 XML 파일 설정, 애너테이션 추가 등의 방법을 제공한다.

### 2.2.3 관점 지향 프로그래밍
- AOP (Aspect Oriented Programming): 프로그래밍에 대한 관심을 핵심 관점, 부가 관점으로 나누어서 관심 기준으로 모듈화하는 것을 의미한다.   
- Ex. 계좌 이체, 고객 관리하는 프로그램   
    - 핵심 관점: 계좌 이체, 고객 관리 로직   
    - 부가 관점: 로깅, 데이터베이스 연결 로직   
- 부가 관점 코드를 핵심 관점 코드에서 분리할 수 있게 해준다. → 개발자가 핵심 관점 코드에만 집중할 수 있고, 프로그램의 변경과 확장에도 유연하게 대응 가능

### 2.2.4 이식 가능한 서비스 추상화
- PSA (Portable Service Abstraction): 스프링에서 제공하는 다양한 기술들을 추상화해 개발자가 쉽게 사용하는 인터페이스
- Ex. 클라이언트의 매핑과 클래스, 메서드의 매핑을 위한 애너테이션 → 스프링에서 데이터베이스에 접근하기 위한 기술(JPA,MyBatis 등)에서 어떤 것을 사용하든 일관된 방식으로 데이터베이스에 접근하도록 인터페이스를 지원

## 스프링 핵심 4가지 정리
1. IoC: 객체의 생성과 관리를 개발자가 하는 것이 아니라 프레임워크가 대신 하는 것
2. DI: 외부에서 객체를 주입받아 사용하는 것
3. AOP: 프로그래밍을 할 때 핵심 관점과 부가 관점을 나누어서 개발하는 것
4. PSA: 어느 기술을 사용하든 일관된 방식으로 처리하도록 하는 것   

## 2.3 스프링 부트 3 둘러보기

### 2.3.1 첫 번째 스프링 부트 3 예제 만들기
http://localhost:8080/test → localhost 는 현재 사용 중인 컴퓨터를 의미(127.0.0.1, 루프백 호스트명), 8080은 스프링 부트의 포트 번호, /test는 스프링 부트에서 설정한 경로

### 2.3.2 스프링 부트 스타터 살펴보기
spring-boot-starter: 의존성이 모여있는 그룹. 명명 규칙 존재 (spring-boot-starter-{작업유형})
- 자주 사용하는 스타터
  - spring-boot-starter-web: SpringMVC 를 사용해서 RESTful 웹 서비스를 개발할 때 필요한 의존성 모음
  - spring-boot-starter-test: 스프링 어플리케이션을 테스트하기 위해 필요한 의존성 모음
  - spring-boot-starter-validation: 유효성 검사를 위해 필요한 의존성 모음
  - spring-boot-starter-actuator: 모니터링을 위해 어플리케이션에서 제공하는 다양한 정보를 제공하기 쉽게 하는 의존성 모음
  - spring-boot-starter-jpa: ORM을 사용하기 위한 인터페이스의 모음인 JPA를 더 쉽게 사용하기 위한 의존성 모음

### 2.3.3 자동 구성
- 스프링 부트에서는 어플리케이션이 최소한의 설정만으로도 실행되게 여러 부분을 자동으로 구성
- 알아야 하는 이유: 추후 개발을 하다가 스프링에서 자동으로 어떻게 구성했는지 확인할 상황이 올 수 있음
- 자동 설정은 META-INF 에 있는 spring.factories 파일에 담겨있다.

### 2.3.4 스프링 부트 3와 자바 버전
스프링 부트 3 이전과 이후는 사용할 수 있는 자바 버전의 범위가 다르다. 
- 스프링 부트 2: 자바 8 버전 이상 사용
- 스프링 부트 3: 자바 17 버전 이상 사용

#### 자바 17의 주요 변화
- 텍스트 블록   
    이전에는 여러 줄의 텍스트를 작성하려면 \n 을 추가해야했으나,   
    이제는 """ 로 감싼 텍스트를 사용해 표현 가능
    ```java
    String query17 = """
  SELECT * FROM "items"
  WHERE "status" = "ON_SALE"
  ORDER BY "price";
  """;
  ```
- formatted() 메서드   
    값을 파싱하기 위한 formatted() 메서드 제공
    ```java
    String tb17 = """
    {
    "id" : %d
    "name" : %s,
    }
    """.formatted(2,"juice");
    ```
- 레코드   
    데이터 전달을 목적으로 하는 객체를 더 빠르고 간편하게 만들기 위한 레코드 기능 제공.   
    레코드는 상속을 할 수 없고, 파라미터에 정의한 필드는 private final로 정의된다   
    레코드는 getter 를 자동으로 만들기 때문에 어노테이션이나 메서드로 따로 정의하지 않아도 된다.   
    ```java
    record Item(String name, int price){
    //이렇게 하면 파라미터가 private final 로 정의됨
  }
  Item juice = new Item("juice",3000);
  juice.price(); //3000
    ```
- 패턴 매칭
    타입 확인을 위해 사용하던 instanceof 키워드를 조금 더 쉽게 사용할 수 있게 해준다.   
    형 변환을 한 다음 바로 사용 가능
    ```java
    if(o instanceof Integer i) {
  }
    ```
- 자료형에 맞는 case 처리
    switch-case 문 사용할 때 자료형에 맞게 case 처리 가능
- Servlet, JPA의 네임 스페이스가 Jakarta로 대체
    javax.* → jakarta.*
- GraalVM 기반의 스프링 네이티브 공식 지원
    기존에 사용하던 JVM에 비해 훨씬 훨씬 빠르게 시작되고 더 적은 메모리 공간을 차지

## 2.4 스프링 부트 3 코드 이해하기

### 2.4.1 @SpringBootApplicatoin 이해하기
```java
@SpringBootApplication
public class SpringBootDeveloperApplication{
    public static void main(String[] args){
        SpringApplication.run(SpringBootDeveloperApplication.class,args);
    }
}
```
- 자바의 main() 메서드와 같은 역할을 하는 클래스
- 어노테이션: 스프링 부트 사용에 필요한 기본 설정을 해준다.
- SpringApplication.run(): 어플리케이션 실행. 첫 번째 인수는 스프링부트3 어플리케이션의 메인 클래스로 사용할 클래스, 두 번째 인수는 커맨드 라인의 인수들을 전달

#### @SpringBootConfiguration
- 스프링 부트 관련 설정을 나타내는 어노테이션   
- @Configuration 을 상속해서 만든 어노테이션
- 개발자가 직접 사용하는 어노테이션은 X

#### @ComponentScan
- 사용자가 등록한 빈을 읽고 등록하는 어노테이션
- ```@Component``` 라는 어노테이션을 가진 클래스들을 찾아 빈으로 등록하는 역할
- @Component 를 감싸는 어노테이션
    - ```@Configuration``` 설정 파일 등록
    - ```@Repository``` ORM 매핑
    - ```@Controller, @RestController``` 라우터
    - ```@Service``` 비즈니스 로직

#### @EnableAutoConfiguration
- 스프링 부트에서 자동 구성을 활성화하는 어노테이션
- 스프링 부트 서버가 실행될 때 스프링 부트의 메타 파일을 읽고 정의된 설정들을 자동으로 구성하는 역할을 수행
- spring.factories 파일의 클래스들이 모두 해당 어노테이션을 사용할 때 자동 설정된다.

### 2.4.2 테스트 컨트롤러 살펴보기
```java
@RestController
public class TestController{
    @GetMapping("/test")
    public String test(){
        return "Hello, world!";
    }
} /* /test 라는 GET 요청이 왔을 때 test()m 메서드를 실행하도록 구성 */
```
- @RestController: 라우터(HTTP 요청과 메서드를 연결하는 장치) 역할을 하는 어노테이션
- @RestController 와 @Component 어노테이션이 용어가 다른데도 같은 @Component 처럼 취급되는 이유
    - 어노테이션의 코드를 보면, @Contoller, @ResponseBody 어노테이션이 합쳐진 결과물이 @RestController 어노테이션임을 알 수 있음
    - @Controller 어노테이션 코드를 보면, @Component 어노테이션을 발견 가능
    - 결론: @Controller 어노테이션이 @ComponentScan 을 통해 빈으로 등록되는 이유 = @Controller 어노테이션에서 @Component 어노테이션을 가지고 있기 때문
