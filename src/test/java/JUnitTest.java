import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {
    @DisplayName("1 +2는 3이다")  // @DisplayName 애너태이션은 테스트 이름을 명시
    @Test                        // @Test 애너테이션을 붙인 메서드는 테스트를 수행하는 메서드
    public void juitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;

        Assertions.assertEquals(sum, a + b);  // 값이 같은지 확인
    }
}
