package me.sonminseo.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.sonminseo.springbootdeveloper.domain.Article;
import me.sonminseo.springbootdeveloper.dto.AddArticleRequest;
import me.sonminseo.springbootdeveloper.dto.UpdateArticleRequest;
import me.sonminseo.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName) { // 블로그 글 '추가' '메서드'
        // save(): JpaRepository에서 지원하는 저장 메서드
        // 'AddArticleRequest 클래스'에 저장된 값 -> 'article 데이터베이스'에 저장해줌
        return blogRepository.save(request.toEntity(userName));
    }
    public List<Article> findAll() { // 전체 글 목록 조회 (article 테이블에 저장되어있는 모든 데이터 조회)
        return blogRepository.findAll();
    }

    public Article findById(long id) { // 글 하나 조회 (id를 갖고 조회)
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id)); // 없을 때 발생시키는 예외
    }



    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}