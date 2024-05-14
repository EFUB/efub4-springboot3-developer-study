package me.sonminseo.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.sonminseo.springbootdeveloper.domain.Article;
import me.sonminseo.springbootdeveloper.dto.AddArticleRequest;
import me.sonminseo.springbootdeveloper.dto.ArticleResponse;
import me.sonminseo.springbootdeveloper.dto.UpdateArticleRequest;
import me.sonminseo.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // json 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles") // 저 url로 온 POST 요청을 메서드와 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED) // 성공적으로 생성
                .body(savedArticle); // 생성된 블로그 글 정보를 응답 객체에 담아 전송
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() { // 전체 글 목록 조회 -> 응답
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) { // PathVariable 'url'에서 값을 가져오는 애너테이션
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

}
