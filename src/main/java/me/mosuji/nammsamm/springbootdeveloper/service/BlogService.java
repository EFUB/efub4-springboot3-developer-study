package me.mosuji.nammsamm.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.domain.Article;
import me.mosuji.nammsamm.springbootdeveloper.dto.AddArticleRequest;
import me.mosuji.nammsamm.springbootdeveloper.dto.UpdateArticleRequest;
import me.mosuji.nammsamm.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName) {

        return blogRepository.save(request.toEntity(userName));
    }

    // 모든 글 조회 메서드
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 하나 조회 : 엔티티가 없으면 예외 발생시킴.
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 블로그 글 하나 삭제
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("not found"+id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);


        blogRepository.deleteById(id);
    }

    // 블로그 글 수정
    @Transactional // 트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    // 현재 인증 객체에 담겨 있는 사용자의 정보와 저자의 정보를 비교한다.
    // 우리가 인증을 수행하고 나면 스프링 시큐리티 내에서는 인증 결과(=인증토큰)를
    // SecurityContext 라는 곳에 저장하여, 전역적으로 사용이 가능하도록 한다.
    // 일반적으로 각 쓰레드마다 갖는 공간에 저장된다.
    private static void authorizeArticleAuthor(Article article){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }

    }


}


