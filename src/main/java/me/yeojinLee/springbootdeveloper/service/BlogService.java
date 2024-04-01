package me.yeojinLee.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.yeojinLee.springbootdeveloper.BlogRepository;
import me.yeojinLee.springbootdeveloper.domain.Article;
import me.yeojinLee.springbootdeveloper.dto.AddArticleRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
