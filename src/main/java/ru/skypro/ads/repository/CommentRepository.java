package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    void removeAllByAds(Ads ads);

    Optional<Comment> findCommentByAdsAndId(Ads adsId, Integer id);

    boolean deleteByAdsAndId(Ads ads, Integer id);

    List<Comment> findAllByAds(Ads ads);
}
