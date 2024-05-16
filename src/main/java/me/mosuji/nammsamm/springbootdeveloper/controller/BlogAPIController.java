package me.mosuji.nammsamm.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.domain.Article;
import me.mosuji.nammsamm.springbootdeveloper.dto.AddArticleRequest;
import me.mosuji.nammsamm.springbootdeveloper.dto.ArticleResponse;
import me.mosuji.nammsamm.springbootdeveloper.dto.UpdateArticleRequest;
import me.mosuji.nammsamm.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body 에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
public class BlogAPIController {
    private final BlogService blogService;

    // HTTP 메서드가 POST 일 때 전달받은 URL 과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody 로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request , Principal principal){
        Article savedArticle = blogService.save(request, principal.getName());

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // 전체 글 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    // 블로그 글 하나 조회
    @GetMapping("/api/articles/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // 블로그 글 하나 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    // 블로그 글 하나 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
