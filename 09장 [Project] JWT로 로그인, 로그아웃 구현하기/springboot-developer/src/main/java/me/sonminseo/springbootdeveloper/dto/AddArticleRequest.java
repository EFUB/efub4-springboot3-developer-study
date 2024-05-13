package me.sonminseo.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sonminseo.springbootdeveloper.domain.Article;

@NoArgsConstructor // 기본생성자 추가
@AllArgsConstructor // 모든 필드의 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity() { // 생성자를 사용해 객체 생성 (toEntity: DTO를 엔티티로 만들어주는 메서드)
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}