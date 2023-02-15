package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
