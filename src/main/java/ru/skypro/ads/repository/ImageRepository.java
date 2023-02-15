package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
