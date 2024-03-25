import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest { // 실행후 상세 확인 위해서는 콘솔 좌상단의 체크 눌러야함

    @DisplayName("1 + 2는 3이다") // 테스트 이름을 명시하는 애너테이션
    @Test // 테스트를 수행하는 메서드 (각 테스트 마다 실행 객체를 만들고 삭제, 테스트끼리 영향x)
    public void junitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;

        Assertions.assertEquals(a + b, sum); // 두 값이 같은지 확인 (기대하는 값, 실제 검증할 값)
    }

//    @DisplayName("1 + 3는 4이다")
//    @Test
//    public void junitFailedTest() {
//        int a = 1;
//        int b = 3;
//        int sum = 3;
//
//        Assertions.assertEquals(a + b, sum); // 실패하는 케이스 예시
//    }
}
