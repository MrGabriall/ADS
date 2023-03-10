package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Ads;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Ads findAdsById(Integer id);
    List<Ads> findAllByAuthorId(Integer authorId);
    boolean existsByIdAndAuthor_Username(Integer id, String author_username);

}
