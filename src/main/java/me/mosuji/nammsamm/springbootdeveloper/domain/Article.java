package me.mosuji.nammsamm.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id //id 기본 필드로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // 'title' 이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @Builder // 빌더 패턴으로 객체 생성
    public Article(String author,String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }

    // 블로그 글 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
