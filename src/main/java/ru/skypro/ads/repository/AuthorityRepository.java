package ru.skypro.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.ads.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}
