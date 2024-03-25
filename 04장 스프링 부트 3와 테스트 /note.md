
**❤️04**

- JUnit: 자바 언어를 위한 단위 테스트 **프레임워크** (주로 메서드를 단위로)
    - BeforeAll, BeforeEach, AfterAll, AfterEach 등의 애너테이션 사용하면 여러 테스트 케이스를 생명주기를 갖고 실행할 수 있음
- AssertJ: JUnit과 함께 사용하는 **라이브러리** (테스트를 위한 코드 작성시 가독성 높은 코드를 작성할 수 있게 해줌)

```java
//Ex
Assertions.assertEquals(sum, a + b); // AssertJ 사용하지 않은 경우 
assertThat(a + b).isEqualTo(sum); // AssertJ 사용한 경우
```
