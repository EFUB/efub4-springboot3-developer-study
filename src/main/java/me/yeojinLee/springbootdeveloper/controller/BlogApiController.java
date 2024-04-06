package me.yeojinLee.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.yeojinLee.springbootdeveloper.domain.Article;
import me.yeojinLee.springbootdeveloper.dto.AddArticleRequest;
import me.yeojinLee.springbootdeveloper.dto.ArticleResponse;
import me.yeojinLee.springbootdeveloper.dto.UpdateArticleRequest;
import me.yeojinLee.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    /* 글 생성 */
    @PostMapping("/api/articles")
    // @RequestBody로 본문 요청 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    /* 글 목록 조회 */
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    /* 글 하나 조회 */
    @GetMapping("/api/articles/{id}")  // URL에 있는 id값 파라미터로 들어감
    public ResponseEntity<ArticleResponse> findArticles(@PathVariable long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    /* 글 삭제 */
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    /* 글 수정 */
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updateArticle);
    }
}
