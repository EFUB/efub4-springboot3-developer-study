package me.yeojinLee.springbootdeveloper;

import me.yeojinLee.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받음으로써 save, findAll 등의 메서도 따로 생성할 필요 없이 사용 가능
public interface BlogRepository extends JpaRepository<Article, Long> {
}
