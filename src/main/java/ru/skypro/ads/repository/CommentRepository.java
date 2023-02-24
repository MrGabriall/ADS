package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllById(Integer id);

    Comment findCommentByAdsAndId(Ads ads, Integer id);

    boolean deleteByAdsAndId(Ads ads, Integer id);
}
