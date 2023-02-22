package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Ads;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Ads findAdsById(Integer id);
}
