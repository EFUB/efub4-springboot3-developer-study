package me.mosuji.nammsamm.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.domain.Article;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String title;
    private String content;


    // 빌더 패턴을 이용해 DTO 를 엔티티로 만들어주는 메서드.
    // 이후 블로그 글을 추가할 때 저장할 엔티티로 변환하는 용도로 사용함.
    public Article toEntity(){  // 생성자를 사용해 객체 생성
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

}
