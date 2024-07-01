package me.crHwang0822.springbootdeveloper.repository;

import me.crHwang0822.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
