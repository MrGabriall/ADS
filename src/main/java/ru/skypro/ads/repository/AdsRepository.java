package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.User;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Ads findAdsById(Integer id);
    List<Ads> findAllByAuthor(User user);
    List<Ads> findAllByAuthorId(Integer authorId);
}
