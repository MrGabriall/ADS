package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Integer, Avatar> {
}
