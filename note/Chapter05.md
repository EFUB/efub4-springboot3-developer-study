## 5.1 데이터베이스란?

### 5.1.1 데이터베이스 관리자, DBMS
DBMS: 데이터베이스를 관리하기 위한 소프트웨어. Ex. MySQL, Oracle

#### 관계형 DBMS (RDBMS)
- 관계형 모델을 기반으로 하는 DBMS
- 테이블 형태로 이루어진 데이터 저장소

#### H2, MySQL
- H2
  - 자바로 작성되어있는 RDBMS
  - 스프링 부트가 지원하는 인메모리 관계형 데이터베이스
  - 어플리케이션 자체 내부에 데이터를 저장하므로 어플리케이션을 다시 실행하면 데이터 초기화
  - 개발 시 테스트 용도로 많이 사용하고 실제 서비스에는 X


## 5.2 ORM이란?
- ORM: 자바의 객체와 데이터베이스를 연결하는 프로그래밍 기법
- 객체와 데이터베이스를 연결해 자바 언어로만 데이터베이스를 다룰 수 있게 하는 도구 
- 장점
  - SQL을 직접 작성하지 않고 사용하는 언어로 데이터베이스에 접근할 수 있다.
  - 객체지향적으로 코드를 작성할 수 있기 때문에 비즈니스 로직에만 집중할 수 있다.
  - 데이터베이스 시스템이 추상화되어 있기 때문에 MySQL에서 PostgreSQL으로 전환한다고 해도 추가 작업이 거의 없다. → 데이터베이스 시스템에 대한 종속성이 줄어든다
  - 매핑하는 정보가 명확하기 때문에 ERD에 대한 의존도를 낮출 수 있고 유지보수할 때 유리하다.
- 단점
  - 프로젝트의 복잡성이 커질수록 사용 난이도도 올라간다.
  - 복잡하고 무거운 쿼리는 ORM으로 해결하지 못하는 경우가 있다.


## 5.3 JPA와 하이버네이트?

- ORM에도 여러 종류가 있으며, 자바는 JPA를 표준으로 사용한다.
- <b>JPA (Java Persistence API)</b>
  - 자바에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스
  - 객체 지향 도메인 모델과 데이터베이스의 다리 역할
- JPA는 인터페이스이므로 실제 사용을 위해서는 ORM 프레임워크를 추가로 선택해야한다. → 하이버네이트
- <b>하이버네이트 (Hibernate)</b>
  - JPA 인터페이스를 구현한 구현체이자 자바용 ORM 프레임워크   
  - 내부적으로는 JDBC API 사용
  - 자바 객체를 통해 데이터베이스 종류에 상관 없이 데이터베이스를 자유자재로 사용할 수 있게 하는 것이 목표

### 5.3.1 엔티티 매니저란?

#### 엔티티
- 데이터베이스의 테이블과 매핑되는 객체   
- 데이터베이스에 영향을 미치는 쿼리를 실행하는 객체

#### 엔티티 매니저
- 엔티티를 관리해 데이터베이스와 어플리케이션 사이에서 객체를 생성, 수정, 삭제하는 등의 역할을 한다.
- 엔티티 매니저 팩토리: 엔티티 매니저를 생성하는 곳
- 스프링 부트의 경우 내부에서 엔티티 매니저 팩토리를 하나만 생성하여 관리하고, @PersistenceContext 혹은 @Autowired 어노테이션을 사용해 엔티티 매니저를 사용한다.
    ```java
    @PersisttentContext
    EntityManager em;  //프록시 엔티티 매니저. 필요할 때 진짜 엔티티 매니저를 호출
    ```
- 스프링 부트는 빈을 하나만 생성해 공유하므로 동시성 문제 발생 가능 → 엔티티 매니저가 아닌 실제 엔티티 매니저와 연결하는 프록시 엔티티 매니저 사용

### 5.3.2 영속성 컨텍스트란?

- 엔티티 매니저는 엔티티를 영속성 컨텍스트에 저장한다.
- 영속성 컨텍스트: 엔티티를 관리하는 가상의 공간
- 영속성 컨텍스트는 아래와 같은 특징들을 지닌다.

    #### 1차 캐시
    - 영속성 컨텍스트는 내부에 1차 캐시를 지닌다.
    - 캐시의 키 = 엔티티의 @Id 어노테이션이 달린 PK 역할의 식별자
    - 캐시의 값 = 엔티티
    - 엔티티를 조회 시 1차 캐시에서 데이터를 조회
      - 값이 있으면 반환 → 데이터베이스를 거치지 않아도 되니 조회 속도가 빠르다.
      - 값이 없으면 데이터베이스에서 조회해 1차 캐시에 저장 후 반환
    
    #### 쓰기 지연
  쓰기 지연: 트랜잭션을 커밋하기 전까지는 데이터베이스에 실제로 질의문을 보내지 않고 쿼리를 모았다가 커밋 시 모았던 쿼리를 한번에 실행하는 것

    #### 변경 감지
  트랜잭션을 커밋하면 1차 캐시에 저장된 엔티티 값과 현재 엔티티 값을 비교해 변경사항이 있다면 변경된 값을 데이터베이스에 자동 반영

    #### 지연 로딩
  쿼리로 요청한 데이터를 어플리케이션에 바로 로딩하는 것이 아니라 필요할 때 쿼리를 날려 데이터를 조회하는 것

### 5.3.3 엔티티의 상태
엔티티는 4가지 상태를 가지며, 특정 메서드를 호출해 변경할 수 있다.
1. 분리: 영속성 컨텍스트가 관리하고 있지 않음
2. 관리: 영속성 컨텍스트가 관리함
3. 비영속: 영속성 컨텍스트와 관계가 없음
4. 삭제
```java
public class EntityManagerTest{
    @Autowired
    EntityManager em;
    
    public void example(){
        //비영속 상태: 엔티티를 처음 만들면 엔티티는 비영속 상태가 된다.
        Member member = new Member(1L,"홍길동");
        
        //관리 상태: Member 객체는 영속성 컨텍스트에서 상태가 관리되게 된다.
        em.persist(member); 
        //분리 상태
        em.detach(member);
        //삭제 상태: 객체를 영속성 컨텍스트와 데이터베이스에서 삭제
        em.remove(member);
    }
}
```

## 5.4 스프링 데이터와 스프링 데이터 JPA
- 스프링 데이터는 비즈니스 로직에 더 집중할 수 있게 데이터베이스 사용 기능을 클래스 레벨에서 추상화
- 인터페이스를 통해 스프링 데이터 사용 가능
- CRUD, 쿼리 자동생성, 페이징 처리, 메서드 이름으로 쿼리 자동 빌딩 등의 기능 제공

### 5.4.1 스프링 데이터 JPA란?
- 스프링 데이터의 공통적인 기능에서 JPA의 유용한 기술이 추가된 기술
- 스프링 데이터의 인터페이스인 PagingAndSortingRepository를 상속 받아 JpaRepository 인터페이스를 만듦
- JpaRepository를 우리가 만든 인터페이스에서 상속 받은 후 제네릭에 <엔티티 이름, 엔티티 PK 타입> 을 입력하면 기본 CRUD 메서드 사용 가능
```
public interface MemberRepository extends JpaRepository<Member,Long>{
}
```

### 5.4.2 스프링 데이터 JPA에서 제공하는 메서드 사용해보기
```java
@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public void test(){
        //생성
        memberRepository.save(new Member(1L,"A"));

        //조회
        Optional<Member> member = memberRepository.findById(1L);
        List<Member> allMembers = memberRepository.findAll();

        //삭제
        memberRepository.deleteById(1L);
    }
}
```
- save()
  - 데이터 객체 저장
  - 전달 인수로 엔티티 Member를 넘기면 반환값으로 저장한 엔티티를 반환받을 수 있다.
- findById(): id를 지정해 엔티티 하나 조회
- findAll(): 전체 엔티티 조회
- deleteById(): id를 지정하면 엔티티 삭제

## 5.2 예제 코드 살펴보기
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //2. 기본 생성자
@AllArgsConstructor
@Getter
@Entity  //1. 엔티티로 지정
public class Member {
    @Id  //3. id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //4. 기본키를 자동으로 1씩 증가
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="name", nullable = false) //5. name이라는 not null 컬럼과 매핑
    private String name;
}
```
#### [1] @Entity 어노테이션
  - Member 객체를 JPA가 관리하는 엔티티로 지정
  - name 속성 지정 시 name의 값과 같은 이름의 테이블과 매핑, 지정하지 않을 시 클래스 이름과 같은 이름의 테이블과 매핑 
#### [2] protected 기본 생성자   
  - 엔티티는 반드시 기본 생성자가 있어야 하며, 접근 제어자는 public 또는 protected여야 한다.
#### [4] @GeneratedValue
  - 기본키의 생성 방식 결정
  - 자동키 생성 설정 방식
    - AUTO: 선택한 데이터베이스 방언에 따라 방식을 자동으로 선택 (기본값)
    - IDENTITY: 기본키 생성을 데이터베이스에 위임 (=AUTO_INCREMENT)
    - SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본키를 할당하는 방법. 오라클에서 주로 사용
    - TABLE: 키 생성 테이블 사용
#### [5] @Column
  - 데이터베이스의 컬럼과 필드를 매핑
  - 아래와 같은 속성을 지닌다. 
    - name: 필드와 매핑할 컬럼 이름. 설정하지 않으면 필드의 이름으로 지정
    - nullable: 컬럼의 null 허용 여부. 설정하지 않으면 true(nullable)
    - unique: 컬럼의 unique 여부. 설정하지 않으면 false(non-unique)
    - columnDefinition: 컬럼 정보 설정. default 값을 줄 수 있다.


## 학습 마무리
- ORM: 관계형 데이터베이스와 프로그램 간의 통신 개념
- JPA: 자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 기술 명세
- Hibernate: JPA의 구현체
- Spring Data JPA: JPA를 쓰기 편하게 만들어놓은 모듈
