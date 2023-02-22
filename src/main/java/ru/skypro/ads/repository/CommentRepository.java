package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllById(Integer id);

    Comment findCommentByAdsIdAndId(Ads adsId, Integer id);

    boolean deleteByAdsIdAndId(Ads adsId, Integer id);
}
