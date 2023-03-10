package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    void removeAllByAds(Ads ads);

    Optional<Comment> findCommentByAdsAndId(Ads adsId, Integer id);
    
    @Transactional(isolation = Isolation.READ_COMMITTED)
    Integer removeCommentById(Integer id);

    List<Comment> findAllByAds(Ads ads);

    boolean existsByIdAndAuthor_Username(Integer id, String author_username);
}
