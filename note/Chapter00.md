## 0.1 인텔리제이 설치 및 설정
인텔리제이: 자바 통합 개발 환경 소프트웨어 

### 그레이들과 메이븐의 차이
- 그레이들과 메이븐은 소스코드를 이용해서 실행 가능한 어플리케이션을 생성하는 과정을 자동화하는 프로그램으로,   
이 과정에는 의존성 내려받기, 코드 패키징, 컴파일, 테스트 실행 등이 포함된다.

- Gradle 의 특징
  1. 메이븐에 비해 가독성이 좋고 설정이 간단하다.
  2. 자바, 코틀린, 그루비 등 다양한 언어를 지원하며, 원하는대로 빌드 스크립트를 작성할 수 있다.
  3. 빌드와 테스트 속도가 메이븐에 비해 더 빠르다.

## 0.2 스프링 부트3 프로젝트 만들기

- 그레이들 프로젝트 → 스프링 부트 3 프로젝트   
  - build.gradle 파일 수정하기
    ```
    plugins{ 
    id 'java'
    id 'org.springframework.boot' version '3.0.2'
    id 'io.spring.dependency-management' version '1.1.0'
    }
    
    group = 'me.crHwang0822' //지정한 그룹 이름
    version = '1.0'
    sourceCompatibility = '17'
    
    repositories { //의존성을 받을 저장소 지정
      mavenCentral()
    }
    
    dependencies { //프로젝트를 개발하며 필요한 기능의 의존성 입력
      implementation 'org.springframework.boot::spring-boot-starter-web'
      testImplementation 'org.springframework.boot::spring-boot-starter-test'
    }
    
    test {
      useJUnitPlatform()
    }
    ```

  - <프로젝트_이름><Application> 이름 형식의 메인 클래스 생성
    ```java
    package me.crHwang0822.springbootdeveloper;
    
    @SpringBootApplication
    public class SpringBootDeveloperApplication{
        public static void main(String[] args){
            SpringApplication.run(SpringBootDeveloperApplication.class,args);
        }   
    }
    ```

## 0.3 포스트맨 설치하기

- 포스트맨: HTTP 요청을 보낼 수 있는 클라이언트 프로그램
- API: 사용자와 서버가 통신하기 위한 인터페이스 
- HTTP 메서드: HTTP 요청을 할 때 클라이언트가 서버에게 어떤 동작을 요청할 것인지 표현하는 명령어

## 0.5 개발 편의와 속도를 확 올려줄 꿀 단축키
- 전체 선택 ```Ctrl A```
- 현재 파일에서 찾기 ```Ctrl F```
- 전체 파일에서 찾기 ```Ctrl Shift F```
- 현재 파일에서 바꾸기 ```Ctrl R```
- 전체 파일에서 바꾸기 ```Ctrl Shift R```
- 줄 복사 ```Ctrl D```
<br><br>

- 실행 ```Shift F10```
- 디버그 모드로 실행 ```Shift F9```
- 사용하지 않는 임포트문 삭제 ```Ctrl Alt O```
