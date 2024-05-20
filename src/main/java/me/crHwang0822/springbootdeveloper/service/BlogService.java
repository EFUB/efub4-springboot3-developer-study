package me.crHwang0822.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.crHwang0822.springbootdeveloper.domain.Article;
import me.crHwang0822.springbootdeveloper.dto.AddArticleRequest;
import me.crHwang0822.springbootdeveloper.dto.UpdateArticleRequest;
import me.crHwang0822.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@Service  //빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName){
        return blogRepository.save(request.toEntity(userName));
    }

    //저장되어 있는 모든 글 가져오기
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    //글 하나 조회
    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ id));
    }

    //블로그 글 삭제
    public void delete(long id){
        Article article = blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("not found : " + id));
        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    //블로그 글 수정
    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " +  id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(),request.getContent());
        blogRepository.save(article);

        return article;

    }

    //게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(username)){
            throw new IllegalArgumentException("not authorized");
        }
    }

}
