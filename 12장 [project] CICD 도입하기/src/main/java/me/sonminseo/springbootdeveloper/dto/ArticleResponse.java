package me.sonminseo.springbootdeveloper.dto;

import lombok.Getter;
import me.sonminseo.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {

    private final String title; // 제목 필드
    private final String content; // 내용 필드

    public ArticleResponse(Article article) { // 엔티티 타입인 Article 을 인수로 전달받음!
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}