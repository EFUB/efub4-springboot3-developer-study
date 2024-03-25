package me.sonminseo.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// 멤버 테이블과 멤버 클래스를 매핑하는 작업을 여기서..
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {  // DB에서 데이터를 가져오는 퍼시스턴트 계층 역할, member라는 이름의 테이블에 접근해서 Member 클래스에 매핑하는 구현체

    // 리포지토리: 엔티티에 있는 데이터를 CRUD 할 때 사용하는 '인터페이스', JpaRepository를 상속받아 구현
    // JpaRepository: 스프링 데이터 JPA에서 제공하는 인터페이스,
}
