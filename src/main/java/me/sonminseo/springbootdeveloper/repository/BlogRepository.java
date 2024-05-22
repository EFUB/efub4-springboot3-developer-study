package me.sonminseo.springbootdeveloper.repository;

import me.sonminseo.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> { // Article: 엔티티, Long: primary key -> jparepository를 상속받을때 인수로
}
