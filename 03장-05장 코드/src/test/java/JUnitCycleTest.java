import org.junit.jupiter.api.*;

public class JUnitCycleTest {

    // BeforeAll -> [ BeforeEach -> Test -> AfterEach ] -> AfterAll
    // BeforeEach ~ AfterEach 생명주기 : 테스트 개수 만큼 반복

    @BeforeAll // 클래스 레벨 설정
    static void beforeAll() { // 이 메서드는 전체 테스트를 시작하기 전에 1회 실행하기 때문에 static으로 선언 (ex 데이터베이스 연결, 테스트환경 초기화)
        System.out.println("@BeforeAll");
    }

    @BeforeEach // 메서드 레벨 설정
    public void beforeEach() { // 테스트 케이스를 시작하기 전마다 실행 (ex 테스트 메서드에서 사용되는 객체 초기화, 테스트에 필요한 값 미리 넣기)
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    @AfterAll // 클래스 레벨 정리
    static void afterAll() { // 이 메서드는 전체 테스트를 마치고 종료하기 전에 1회 실행하기 때문에 static으로 선언 (ex 데이터베이스 연결 종료, 공통 사용 자원 해제)
        System.out.println("@AfterAll");
    }

    @AfterEach // 메서드 레벨 정리
    public void afterEach() { // 테스트 케이스를 종료하기 전마다 실행 (ex 테스트 하나 직후 특정 데이터 삭제)
        System.out.println("@AfterEach");
    }

}
