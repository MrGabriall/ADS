package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
